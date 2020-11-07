package com.wx.utils;

import java.io.File;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;

public final class ImageUtil {
	private static String codeImgPath = "D://tempImg/code.jpg";
	private static String headImgPath = "D://tempImg/head.jpg";
	// 目标
	private static String targImgPath = "D://tempImg/targ.jpg";
	private static String bigBgPath = "D://tempImg/bigBg.jpg";
	// 结果产品
	private static String endPath = "D://tempImg/end";
	
	
	
	public static void dowloadImg(String headUrl, String codeUrl, String msg) {
		HttpUtil.downloadFileFromUrl(codeUrl, codeImgPath);
		HttpUtil.downloadFileFromUrl(headUrl, headImgPath);
		// 合成
		headAndCodeImg();
		endImg(msg);
	}
		
	private static void headAndCodeImg() {
		File headFile = new File(headImgPath);
		File codeFile = new File(codeImgPath);
		
		if(!codeFile.exists() && !headFile.exists()) {
			System.out.println("图片不存在");
			return;
		}
		
		ImgUtil.pressImage(FileUtil.file(codeImgPath), FileUtil.file(targImgPath), ImgUtil.read(FileUtil.file(headImgPath)), 0, 0, 1f);
		codeFile.delete();
		headFile.delete();
		
	}
	
	private static String endImg(String msg) {
		
		File targImgFile = new File(targImgPath);
		File bigBgFile = new File(bigBgPath);
		File endFile = new File(endPath);
		
		if(!targImgFile.exists() && !bigBgFile.exists()) {
			System.out.println("图片不存在");
			return "";
		}
		if(!endFile.exists()) {
			endFile.mkdirs();
		}
		File[] listFiles = endFile.listFiles();
		
		double count = Math.random();
		
		for (File file : listFiles) {
			if(!file.getName().equals(endPath+count+".jpg")) {
				count = Math.random();
			}
		}
		
		ImgUtil.scale(FileUtil.file(targImgPath), FileUtil.file(targImgPath), 0.5f);
		ImgUtil.pressImage(FileUtil.file(bigBgPath), FileUtil.file(endPath+"/"+count+".jpg"), ImgUtil.read(FileUtil.file(targImgPath)), 230, 350, 1f);
		targImgFile.delete();
		// 写内容
//		ImgUtil.
		System.out.println(endPath+"/"+count+".jpg");
		return endPath+"/"+count+".jpg";
	}
	
	
	public void method() {
		String headUrl = "http://thirdwx.qlogo.cn/mmopen/PiajxSqBRaELplq9ic6RqJKLWnnQ7FZiaV63zd6dNSibYtt9DTdv09tkDicWicezbOXlExJBt9T2xC1rhXVM2SHuBVzA/132";
		String codeUrl = "http://mmbiz.qpic.cn/mmbiz_jpg/gSHWud43k2ka5ARtVD5JfGngfia1B3VdVCdZJoSyLrHTJvNZQlEZftkPiaMjMpLXYib3shOsztTTRdalNzWJd3dIA/0";
		dowloadImg(headUrl, codeUrl,"欢迎您");

		
	}
	
	
	
}
