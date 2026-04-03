package hy.ea.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 图片操作
 * 
 * @author yaloo
 * 
 */
public class ImageCut {
	/**
	 * @Description: 裁剪 将srcImageFile裁剪后生成dirImageFile
	 * @param srcImageFile
	 *            原始图
	 * @param dirImageFile
	 *            目标图 生成路径，名称
	 * @param x
	 *            left距左距离
	 * @param y
	 *            top距上距离
	 * @param destWidth
	 *            裁剪宽度
	 * @param destHeight
	 *            裁剪高度
	 */
	public static void abscut(String srcImageFile, String dirImageFile, int x,
			int y, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			//
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getWidth(); //
			int srcHeight = bi.getHeight(); //
			if (srcWidth >= destWidth && srcHeight >= destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(destWidth, destHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null); //
				g.dispose();
				//
				ImageIO.write(tag, "JPEG", new File(dirImageFile));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description按照原比例缩放或者放大图片存储
	 * @param srcImageFile
	 *            原图片路径
	 * @param result
	 *            目标图片存储路径+名字
	 * @param scale
	 *            倍数
	 * @param flag
	 *            true按照倍数放大，false按照倍数缩小
	 */
	public static void scale(String srcImageFile, String result, int scale,
			boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); //
			int width = src.getWidth(); //
			int height = src.getHeight(); //
			if (flag) {
				//
				width = width * scale;
				height = height * scale;
			} else {
				//
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); //
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));//
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照原比例缩放,如果设定生成宽高大于原图片那么生成结果为原图片
	 * 
	 * @param srcImageFile
	 *            原图片
	 * @param result
	 *            存储目录+文件名称
	 * @param _width
	 *            生成图片的宽度
	 * @param _height
	 *            生成图片的高度
	 */
	public static void scale(String srcImageFile, String result, int _width,
			int _height) {
		scale(srcImageFile, result, _width, _height, 0, 0, true);
	}

	public static void scale(String srcImageFile, String result, int width,
			int height, int x, int y, boolean boo) {
		try {

			BufferedImage src = ImageIO.read(new File(srcImageFile)); //
			if (src != null) {
				int w = src.getWidth(); //
				int h = src.getHeight(); //
				int ww = 0;
				int hh = 0;

				if (w > 0 && h > 0) {
					if (boo) {
						if (w / h >= width / height) {
							if (w > width) {
								ww = width;
								hh = (h * width) / w;
							} else {
								ww = w;
								hh = h;
							}
						} else {
							if (h > height) {
								hh = height;
								ww = (w * height) / h;
							} else {
								ww = w;
								hh = h;
							}
						}
					} else {
						if (w < h) {
							if (w > width) {
								ww = width;
								hh = (h * width) / w;
							} else {
								ww = w;
								hh = h;
							}
						} else {
							if (h > height) {
								hh = height;
								ww = (w * height) / h;
							} else {
								ww = w;
								hh = h;
							}
						}
					}
				}

				Image image = src
						.getScaledInstance(ww, hh, Image.SCALE_DEFAULT);
				BufferedImage tag = new BufferedImage(ww, hh,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(image, x, y, null); //
				g.dispose();
				ImageIO.write(tag, "JPEG", new File(result));//
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照原比例缩放,如果设定生成宽高大于原图片那么生成结果为原图片
	 * 
	 * @param srcImageFile
	 *            原图片
	 * @param result
	 *            存储目录+文件名称
	 * @param _width
	 *            生成图片的宽度
	 * @param _height
	 *            生成图片的高度
	 */
	public static void systemScale(String srcImageFile, String result,
			int _width, int _height) {
		scale(srcImageFile, result, _width, _height, 0, 0, false);
	}

	/**
	 * 图片转换成JPG GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将原图片变灰
	 * 
	 * @param source
	 * @param result
	 */
	public static void gray(String source, String result) {
		try {
			BufferedImage src = ImageIO.read(new File(source));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "http://www.impf2010.com/upload_files/company201009046vxdyzy4wg0000000025/contactcompany/2016-01-16/52e0ec4bccfc4f96895d509c1990d20b.png";
		ImageCut.scale(url, "C:/Users/Administrator/Pictures/i1.png", 45, 45);
	}
}
