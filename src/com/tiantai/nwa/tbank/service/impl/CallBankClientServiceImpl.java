package com.tiantai.nwa.tbank.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.tiantai.nwa.tbank.bean.CallBankRequestBean;
import com.tiantai.nwa.tbank.bean.CallBankReturnBean;
import com.tiantai.nwa.tbank.service.CallBankClientService;
import com.tiantai.nwa.util.CallBankServiceClientUtil;

/**
 * 
 * @author zhb
 * 
 */
public class CallBankClientServiceImpl implements CallBankClientService {

	/**
	 * http方式
	 */
	@Override
	public CallBankReturnBean CallBankClientHttp(CallBankRequestBean reqBean) {
		String url = "http://" + reqBean.getServer() + ":" + reqBean.getPort();
		PostMethod postMethod = new PostMethod(url);
		String postXML = reqBean.getReqDatagram();
		String returnXML = "";

		try {
			postMethod.setRequestEntity(new StringRequestEntity(postXML,
					"text/html", "utf-8"));
			postMethod
					.setRequestHeader("Content-type", "text/xml; charset=GBK");
			HttpClient httpClient = new HttpClient();
			int resultint = httpClient.executeMethod(postMethod);
			if (resultint == 200) {// 正确返回
				returnXML = postMethod.getResponseBodyAsString();
			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		try {
			return CallBankServiceClientUtil.makeCallBankReturnBean(returnXML,
					reqBean);
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * socket方式
	 * 
	 * @param reqBean
	 * @param bpbBean
	 * @return
	 */
	@Override
	public CallBankReturnBean CallBankClientBIOSocket(
			CallBankRequestBean reqBean) {
		StringBuffer sb = new StringBuffer();
		Socket socket = null;
		BufferedReader serverIn = null;
		PrintWriter out = null;
		String server = reqBean.getServer();
		String port = reqBean.getPort();

		try {
			socket = new Socket(server, Integer.parseInt(port));
			socket.setSoTimeout(5000);
			serverIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			out.println(reqBean.getReqDatagram());// 发送请求报文

			String line = null;
			while ((line = serverIn.readLine()) != null && line.length() != 0) {
				sb.append(line);
				logger.info("值：{}", line);
			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		} finally {
			try {
				serverIn.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				logger.error("操作异常", e);
			}

		}

		try {
			return CallBankServiceClientUtil.makeCallBankReturnBean(
					sb.toString(), reqBean);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return null;
	}

	@Override
	public CallBankReturnBean CallBankClientNIOSocket(CallBankRequestBean reqBean) {
		SocketChannel socketChannel = null;
		Selector selector = null;
		String server = reqBean.getServer();
		String port = reqBean.getPort();
		StringBuffer sb = new StringBuffer();

		try {
			InetSocketAddress addr = new InetSocketAddress(server,
					Integer.parseInt(port));
			socketChannel = SocketChannel.open();

			selector = Selector.open();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);

			// 连接到server
			socketChannel.connect(addr);
			while (!socketChannel.finishConnect()) {// 完成套接字通道的连接过程
			}
			// 发送消息
			ByteBuffer buffer = ByteBuffer.wrap(reqBean.getReqDatagram()
					.getBytes("gb2312"));//gb2312  iso8859-1 utf-8
			while (buffer.hasRemaining()) {
				socketChannel.write(buffer);// 向服务器发送消息
			}
			// 接收消息
			boolean flag = true;
			while (flag) {
				int select = selector.select();
				if (select > 0) {
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> iter = keys.iterator();
					while (iter.hasNext()) {
						SelectionKey sk = iter.next();
						if (sk.isReadable()) {// 测试此键的通道是否已准备好进行读取
							SocketChannel curSc = (SocketChannel) sk.channel();
							ByteBuffer buffer2 = ByteBuffer.allocate(4096);
							while (curSc.read(buffer2) > 0) {
								//buffer2.flip();
								sb.append(new String(buffer2.array(),"gb2312"));//gbk gb2312  iso8859-1 utf-8
								//sb.append(buffer2.array());								
								buffer2.clear();
							}
							flag = false;
						}
						iter.remove();
					}
				}
			}
			// 循环结束
		} catch (Exception e) {
			logger.error("操作异常", e);
			return null;
		} finally {
			// 关闭
			try {
				if (selector != null && selector.isOpen()) {
					selector.close();
				}
				if (socketChannel != null && socketChannel.isOpen()) {
					socketChannel.close();
				}
			} catch (Exception e2) {
			}

		}

		try {
			return CallBankServiceClientUtil.makeCallBankReturnBean(
					sb.toString(), reqBean);
		} catch (Exception e) {
		}

		return null;

	}

}
