package com.basiliskSB.utility;

import com.basiliskSB.dto.order.InvoiceDTO;
import com.lowagie.text.DocumentException;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PDFInvoiceGenerator {

    public static byte[] generateInvoicePdf(InvoiceDTO dto) throws Exception {
        var htmlContent = generateHTML(dto);
        var renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);

        var byteArrayOutputStream = new ByteArrayOutputStream();
        renderer.layout();
        renderer.createPDF(byteArrayOutputStream);

        renderer.finishPDF();
        return byteArrayOutputStream.toByteArray();
    }

    public static String generateHTML(InvoiceDTO dto){

        var stringBuilder = new StringBuilder();
        var header = """
            <html lang="en">
            <head>
            <style>
                body{
                    font-size: 12;
                    color:#34495e;
                    font-family: Helvetica;
                }
                .logo{
                    margin-bottom: 20px;
                    background-color: #131931;
                    padding: 10px;
                }
                .logo > img{
                    height: 30px;
                    vertical-align: middle;
                    margin-right: 10px;
                }
                .logo > span{
                    font-size: 30px;
                    font-weight: bold;
                    color: #14acf8;
                    vertical-align: middle;
                }
                .header{
                    border-bottom: solid 2px black;
                    margin-bottom: 20px;
                }
                .header td{
                    padding-bottom: 10px;
                }
                .header td:first-child{
                    font-weight: bold;
                    color:#7f8c8d;
                    padding-right: 20px;
                }
                .details{
                    width: 100%;
                }
                .details th{
                    text-align: left;
                }
                .details td{
                    padding: 8px;
                }
                .details td:last-child{
                    text-align: right;
                    padding: 8px 0 8px 8px;
                }
                .result{
                    text-align: right;
                    padding: 8px 0;
                    font-weight: bold;
                }
                .result > span{
                    margin-right: 15px;
                }
            </style>
            </head>
            <body>""";
        stringBuilder.append(header);

        var logo = """
                <div class="logo">
                    <img src="http://localhost:7070/resources/image/logo.png" alt="logo" />
                    <span>BASILISK</span>
                </div>""";
        stringBuilder.append(logo);

        var invoiceInformation = String.format("""
                <div class="header">
                    <table>
                        <tbody>
                            <tr>
                                <td>Invoice Number:</td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td>Order Date:</td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td>Customer:</td>
                                <td>%s</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            """, dto.getInvoiceNumber(), dto.getOrderDate(), dto.getCustomerCompany());
        stringBuilder.append(invoiceInformation);

        var deliveryInformation = String.format("""
                <div class="header">
                    <table>
                        <tbody>
                            <tr>
                                <td>Delivery Company:</td>
                                <td>%s</td>
                            </tr>
                            <tr>
                                <td>Address:</td>
                                <td>
                                    <div>%s</div>
                                    <div>%s</div>
                                </td>
                            </tr>
                            <tr>
                                <td>Received By:</td>
                                <td>%s</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            """, dto.getDeliveryCompany(), dto.getDeliveryAddress(), dto.getDeliveryCityAndPostalCode(), dto.getReceivedBy());
        stringBuilder.append(deliveryInformation);

        var detailHeader = """
                <table class="details">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Qty</th>
                            <th>Unit Price</th>
                            <th>Discount</th>
                            <th>Sub Total</th>
                        </tr>
                    </thead>
                    <tbody>
            """;
        stringBuilder.append(detailHeader);

        for(var detail : dto.getDetails()){
            var row = String.format("""
                <tr>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                </tr>""", detail.getProductName(), detail.getQuantity(), detail.getUnitPrice(), detail.getDiscount(), detail.getSubTotal());
            stringBuilder.append(row);
        }

        var endTable = "</tbody></table>";
        stringBuilder.append(endTable);

        var devliveryCost = String.format("""
                <div class="result">
                    <span>Delivery Cost: </span> %s
                </div>
            """, dto.getDeliveryCost());
        stringBuilder.append(devliveryCost);

        var totalCost = String.format("""
                <div class="result">
                    <span>Total: </span> %s
                </div>
            """, dto.getTotalCost());
        stringBuilder.append(totalCost);

        stringBuilder.append("</body> </html>");
        return stringBuilder.toString();
    }
}
