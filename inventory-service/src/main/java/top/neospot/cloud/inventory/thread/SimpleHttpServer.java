//package top.neospot.cloud.inventory.thread;
//
//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * By neo.chen{neocxf@gmail.com} on 2019/8/29.
// */
//public class SimpleHttpServer {
//	static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);
//	static String basePath;
//	static ServerSocket serverSocket;
//	static int port = 8080;
//
//	public static void setPort(int port) {
//		if (port > 0) {
//			SimpleHttpServer.port = port;
//		}
//	}
//
//	public static void setBasePath(String basePath) {
//		if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
//			SimpleHttpServer.basePath = basePath;
//		}
//	}
//
//	public static void start () throws Exception {
//		serverSocket = new ServerSocket(port);
//		Socket socket = null;
//		while ((socket = serverSocket.accept()) != null) {
//			threadPool.execute(new HttpRequestHandler(socket));
//		}
//
//		serverSocket.close();
//	}
//
//
//	static class HttpRequestHandler implements Runnable {
//		private Socket socket;
//
//		public HttpRequestHandler(Socket socket) {
//			this.socket = socket;
//		}
//
//		@Override
//		public void run() {
//			String line = null;
//			BufferedReader br = null;
//			BufferedReader reader = null;
//			PrintWriter out = null;
//			InputStream in = null;
//
//			try {
//				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				String header = reader.readLine();
//				String filePath = basePath + header.split(" ")[1];
//				out = new PrintWriter(socket.getOutputStream());
//				if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
//					in = new FileInputStream(filePath);
//					ByteOutputStream baos = new ByteOutputStream();
//					int i = 0;
//					while ((i = in.read()) != -1) {
//						baos.write(i);
//					}
//
//					byte[] bytes = baos.toByteArray();
//					out.println("HTTP/1.1 200 OK");
//					out.println("Server: Molly");
//					out.println("Content-Type: image/jpeg");
//					out.println("Content-Length: " + bytes.length);
//					out.println("");
//					socket.getOutputStream().write(bytes, 0, bytes.length);
//
//				} else {
//					br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
//					out.println("HTTP/1.1 200 OK");
//					out.println("Server: Molly");
//					out.println("Content-Type: text/html; charset=UTF-8");
//					out.println("");
//					while ((line = br.readLine()) != null) {
//						out.println(line);
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}
