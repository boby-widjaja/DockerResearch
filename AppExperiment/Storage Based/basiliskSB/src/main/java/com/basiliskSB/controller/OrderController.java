package com.basiliskSB.controller;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.factory.abstraction.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Qualifier("orderFactory")
	@Autowired
	private IndexFactory indexFactory;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String invoiceNumber, @RequestParam(required=false) Long customerId, @RequestParam(required=false) String employeeNumber, @RequestParam(required=false) Long deliveryId, @RequestParam(required=false) String orderDate, Model model) {
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("invoiceNumber", invoiceNumber);
		indexFactory.setUrlParameters("customerId", customerId);
		indexFactory.setUrlParameters("employeeNumber", employeeNumber);
		indexFactory.setUrlParameters("deliveryId", deliveryId);
		indexFactory.setUrlParameters("orderDate", orderDate);
		indexFactory.processIndex(model);
		return "order/order-index";
	}

	@Qualifier("orderFactory")
	@Autowired
	private UpsertFactory upsertFactory;
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) String invoiceNumber, Model model) {
		upsertFactory.setId(invoiceNumber);
		upsertFactory.processUpsertForm(model);
		return "order/order-form";
	}

	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertOrderDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "order/order-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/order/index";
		}
	}

	@Qualifier("orderFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) String invoiceNumber, Model model) {
		deleteFactory.processDelete(model);
		return "redirect:/order/index";
	}

	@Qualifier("orderDetailFactory")
	@Autowired
	private IndexDetailFactory indexDetailFactory;
	
	@GetMapping("/detail")
	public String detail(@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = true) String invoiceNumber, Model model) {
		indexDetailFactory.setParentId(invoiceNumber);
		indexDetailFactory.setPage(page);
		indexDetailFactory.processIndex(model);
		return "order/order-detail";
	}

	@Qualifier("orderDetailFactory")
	@Autowired
	private UpsertDetailFactory upsertDetailFactory;
	
	@GetMapping("/upsertDetailForm")
	public String upsertDetailForm(@RequestParam(required = false) Long productId, @RequestParam(required = false) String invoiceNumber, Model model) {
		@Getter @Setter @NoArgsConstructor @AllArgsConstructor
		class CompositeId{
			private String invoiceNumber;
			private Long productId;
		}
		upsertDetailFactory.setParentId(invoiceNumber);
		upsertDetailFactory.setId(new CompositeId(invoiceNumber, productId));
		upsertDetailFactory.processUpsertForm(model);
		return "order/order-detail-form";
	}
	
	@PostMapping("/upsertDetail")
	public String upsertDetail(@Valid @ModelAttribute("dto") UpsertOrderDetailDTO dto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			upsertDetailFactory.processUpsertForm(model, dto);
			return "order/order-detail-form";
		} else {
			upsertDetailFactory.save(dto);
			redirectAttributes.addAttribute("invoiceNumber", dto.getInvoiceNumber());
			return "redirect:/order/detail";
		}
	}

	@Qualifier("orderDetailFactory")
	@Autowired
	private DeleteDetailFactory deleteDetailFactory;

	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes) {
		deleteDetailFactory.setId(id);
		var invoiceNumber = deleteDetailFactory.getParentId();
		deleteDetailFactory.processDelete(model);
		redirectAttributes.addAttribute("invoiceNumber", invoiceNumber);
		return "redirect:/order/detail";
	}

	@Qualifier("customerFactory")
	@Autowired
	private GetFactory getFactory;

	@GetMapping("/customerAddress/{id}")
	public ResponseEntity<Object> getCustomerAddress(@PathVariable(required = true) Long id){
		getFactory.setId(id);
		var dto = getFactory.getData();
		return ResponseEntity.status(200).body(dto);
	}

	@Qualifier("orderFactory")
	@Autowired
	private PDFFactory pdfFactory;

	@GetMapping("/generateInvoice")
	public void generateInvoice(@RequestParam(required = true) String invoiceNumber, HttpServletResponse response) throws Exception {
		pdfFactory.setId(invoiceNumber);
		var pdfBytes = pdfFactory.getPdfByte();
		pdfFactory.setFileName(String.format("%s-invoice", invoiceNumber));
		var fileName = pdfFactory.getFileNameDisposition();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", fileName);
		response.getOutputStream().write(pdfBytes);
		response.getOutputStream().flush();
	}
}
