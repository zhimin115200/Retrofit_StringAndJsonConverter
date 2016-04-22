# Retrofit_StringAndJsonConverter
## client与server以json数据格式通讯，但针对特殊请求或请求数据失败的情况server返回不规则的String类型数据，扩展的retrofit的响应转换器

##使用方法
###step 1:初始化retrofit，在application中restApi作为全局变量：
  Retrofit retrofit = new Retrofit.Builder()  
  		.baseUrl(BASE_TRC_URL)  
  		.addConverterFactory(new JsonAndStringConverters  
  				.QualifiedTypeConverterFactory(GsonConverterFactory.create()  
  						,StringConverterFactory.create()))  
  						.build();  
  restApi = retrofit.create(IRestApi.class);  

###step 2:创建IRestApi.java 
	/**获得QiNiu token*/  
	@GET("/clock/rest/storage/token/get")  
	@string  
	Call<String> getQiNiuToken();//对数据不是json的特殊处理，用StringConverterFactory解析  
	
	@POST("/clock/rest/file/add")  
	@Json  
	Call<ResponseBaseVo> addFile(@Body UpFileVo upFile);//response是json格式的调用默认的gson解析  

###step 3:创建网络请求工厂类NetRequests.java  
public interface IStringListener{  
		void successed(String t);  
		void failed(Throwable t);  
	}  
	/**获得QiNiu token*/  
	public static void getQiNiuToken(final IStringListener listener){  
		Call<String> model = CoupleAlarmApp.getInstance().restApi.getQiNiuToken();  
		model.enqueue(new Callback<String>() {  

			@Override
			public void onFailure(Call<String> arg0, Throwable t) {
				// TODO Auto-generated method stub
				Logger.e(TAG, "getQiNiuToken is onFailure: " + t.getMessage());
				listener.failed(t);
			}

			@Override
			public void onResponse(Call<String> arg0,
					Response<String> response) {
				// TODO Auto-generated method stub
				String responseString = response.body();
				Logger.e(TAG, "getQiNiuToken is onResponse: "+responseString);
				listener.successed(responseString);
			}
		});
	}

	/**向server添加文件记录*/  
	public static void addFile(UpFileVo fileVo, final IResultCallback<ResponseBaseVo> callback) {

		Call<ResponseBaseVo> model = CoupleAlarmApp.getInstance().restApi.addFile(fileVo);
		model.enqueue(new Callback<ResponseBaseVo>() {

			@Override
			public void onFailure(Call<ResponseBaseVo> arg0, Throwable t) {
				// TODO Auto-generated method stub
				Logger.e(TAG, "addFile is onFailure: " + t.getMessage());
				if (callback!=null) {

					callback.failed(t);
				}
			}

			@Override
			public void onResponse(Call<ResponseBaseVo> arg0,
					Response<ResponseBaseVo> response) {
				// TODO Auto-generated method stub
				ResponseBaseVo responseBaseVo = response.body();
				Logger.e(TAG, "addFile is  onResponse: "+responseBaseVo.getDetail());
				if (callback!=null) {

					callback.successed(responseBaseVo);
				}
			}
		});
	}
###step 4:调用
