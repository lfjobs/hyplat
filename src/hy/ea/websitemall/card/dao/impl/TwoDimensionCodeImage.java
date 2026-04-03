package hy.ea.websitemall.card.dao.impl;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class TwoDimensionCodeImage implements QRCodeImage{
	 BufferedImage bufImg;  
     
     public TwoDimensionCodeImage(BufferedImage bufImg) {  
         this.bufImg = bufImg;  
     }  
	@Override
	public int getHeight() {
		return 0;
	}
	@Override
	public int getPixel(int arg0, int arg1) {
		return 0;
	}
	@Override
	public int getWidth() {
		return 0;
	}

}
