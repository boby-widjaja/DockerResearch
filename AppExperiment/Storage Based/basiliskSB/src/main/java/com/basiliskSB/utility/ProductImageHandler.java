package com.basiliskSB.utility;

import com.basiliskSB.dto.product.UpsertProductDTO;

public class ProductImageHandler {
    public static void imageFieldHandler(UpsertProductDTO dto){
        var multipartFile = dto.getImage();
        var isMultipartEmpty = (multipartFile != null) ? multipartFile.isEmpty() : true;
        var imagePath = ((dto.getImagePath() == null || dto.getImagePath().equals(""))
            && isMultipartEmpty) ? null : dto.getImagePath();
        try{
            if(!isMultipartEmpty){
                imagePath = FileHelper.uploadProductPhoto(imagePath, multipartFile);
            }
            dto.setImagePath(imagePath);
        } catch (Exception exception){}
    }
}
