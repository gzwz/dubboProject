package cn.qumiandan.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @description：HttpClient 工具类
 * @author：zhuyangyong
 * @date: 2018/12/03 11:10
 */
@Slf4j
public class HttpClientUtil {

	// 创建Httpclient对象
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		return get(url, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String get(String url, Map<String, String> param) {
		String resultString = "";
		CloseableHttpResponse response = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : param.keySet()) {
					builder.addParameter(key, param.get(key));
				}
			}
			URI uri = builder.build();
			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} catch (Exception e) {
			log.error("{}", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("{}", e);
			}
		}
		return resultString;
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url) {
		return postForm(url, null);
	}

	/**
	 * 发送POST请求（普通表单形式）
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String postForm(String url, Map<String, String> param) {
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<>();
				for (String key : param.keySet()) {
					paramList.add(new BasicNameValuePair(key, param.get(key)));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
				httpPost.setEntity(entity);
			}
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			log.error("{}", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("{}", e);
			}
		}
		return resultString;
	}

	/**
	 * 发送POST请求（JSON形式）
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String postJson(String url, String json) {
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			log.error("{}", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				log.error("{}", e);
			}
		}
		return resultString;
	}

	/**
	 * 使用
	 * @param url
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String doPostForJson(String url, Map<String, String> condition) {
		StringBuilder result = new StringBuilder();
		CloseableHttpClient httpclient = HttpClients.custom().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("charset", "UTF-8");
			String json = GsonHelper.toJson(condition);
			StringEntity stentity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			stentity.setContentEncoding("UTF-8");
			stentity.setContentType("application/json");
			httpPost.setEntity(stentity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result.append(text);
					}
				}
				EntityUtils.consume(entity);
				return result.toString();
			}  finally {
				response.close();
			}
		} catch (Exception e) {
			
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 使用
	 * @param url
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String doPostForString(String url, String jsonstr) {
		StringBuilder result = new StringBuilder();
		CloseableHttpClient httpclient = HttpClients.custom().build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("charset", "UTF-8");
			StringEntity stentity = new StringEntity(jsonstr, "utf-8");// 解决中文乱码问题
			stentity.setContentEncoding("UTF-8");
			stentity.setContentType("application/json");
			httpPost.setEntity(stentity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						result.append(text);
					}
				}
				EntityUtils.consume(entity);
				return result.toString();
			}  finally {
				response.close();
			}
		} catch (Exception e) {
			
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 使用
	 * @param url
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String doGetForJson(String url, Map<String, String> conditions) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建参数队列
		List<NameValuePair> params = new ArrayList<NameValuePair>(conditions.size());
		for(Map.Entry<String, String> condition : conditions.entrySet()) {
			params.add(new BasicNameValuePair(condition.getKey(), condition.getValue()));
		}
		try {
			// 参数转换为字符串
			String paramsStr = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			url = url + "?" + paramsStr;
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Content-Type", "application/json");
			httpget.addHeader("charset", "UTF-8");
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("shopId", 10);
		String url = "http://192.168.101.91:8888/product/getCusProductListByShopId";
		String result = postJson(url, jsonObject.toString());
		System.out.println(result);
	}
}
