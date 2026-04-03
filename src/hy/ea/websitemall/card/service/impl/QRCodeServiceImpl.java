package hy.ea.websitemall.card.service.impl;

import java.io.OutputStream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import hy.ea.websitemall.card.dao.QRCodeDao;
import hy.ea.websitemall.card.service.QRCodeService;
@Service
public class QRCodeServiceImpl implements QRCodeService{
	@Resource
	private QRCodeDao qrCodeDao;
	@Override
	public String encoderQRCode(String content, String imgPath) {
		return qrCodeDao.encoderQRCode(content, imgPath);
	}

	@Override
	public void encoderQRCode(String content, OutputStream output) {	
		qrCodeDao.encoderQRCode(content, output);
	}

	@Override
	public String encoderQRCode(String content, String imgPath, String imgType) {
		return qrCodeDao.encoderQRCode(content, imgPath, imgType);
	}

	@Override
	public void encoderQRCode(String content, OutputStream output,String imgType) {		
		qrCodeDao.encoderQRCode(content, output, imgType);
	}

	@Override
	public String createQRCode(String content, String imgType, String imgPath,
			String ccbPath) {
		return qrCodeDao.createQRCode(content, imgType, imgPath,ccbPath) ;
	}

}
