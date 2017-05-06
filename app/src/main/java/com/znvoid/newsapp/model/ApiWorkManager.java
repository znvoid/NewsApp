package com.znvoid.newsapp.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.znvoid.newsapp.Utils.Util;
import com.znvoid.newsapp.bean.Api;
import com.znvoid.newsapp.bean.Channel;
import com.znvoid.newsapp.bean.ChannelRespondBody;
import com.znvoid.newsapp.bean.ExpressRespondBody;
import com.znvoid.newsapp.bean.Item;
import com.znvoid.newsapp.bean.NewsContentRespondBody;
import com.znvoid.newsapp.bean.NewsRespondBody;
import com.znvoid.newsapp.bean.PageBean;
import com.znvoid.newsapp.bean.Picture;
import com.znvoid.newsapp.bean.PictureRespondBody;
import com.znvoid.newsapp.bean.WeatherRespondBody;
import com.znvoid.newsapp.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zn on 2017/4/9.
 */
public class ApiWorkManager {
    private RequestQueue queue;
    private int page;
    private int allpage = Integer.MAX_VALUE;
    private static ApiWorkManager ourInstance = new ApiWorkManager();

    public static ApiWorkManager getInstance() {
        return ourInstance;
    }
    private Gson gson;
    private ApiWorkManager() {
        gson=new Gson();
    }

    public void init(Context context) {
        if (queue==null)
             queue = Volley.newRequestQueue(context);

    }

    /**
     * 新闻条目获取
     *
     * @param loadLisenter
     * @param channelId
     */
    public void getNews(final NewsLoadLisenter<Item> loadLisenter, final String channelId) {
        if (!Presenter.getInstance().checkNetStat()) {
            String newsString = Presenter.getInstance().getDataFromPerference("news_" + channelId, "");
            if (!newsString.equals("")) {
                List<Item> items = gson.fromJson(newsString, new TypeToken<List<Item>>() {}.getType());
                loadLisenter.onComplete(items);
            } else {
                loadLisenter.onError();
            }
            return;
        }
        new ApiRequest(Api.GET_NEWS_API, new ApiRequest.Listener() {
            @Override
            public void onResponse(String response) {

                try {

//                    ApiRespond<NewsRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<NewsRespondBody>>() {
//                    });
                    ApiRespond<NewsRespondBody> apiRespond=ApiRespond.parserJsonString(response,new TypeToken<ApiRespond<NewsRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0) {
                        PageBean pageBean = apiRespond.getShowapi_res_body().getPagebean();
                        allpage = pageBean.getAllPages();
                        List<Item> items = pageBean.getContentlist();
                        if (items != null) {
                            String itemsString = gson.toJson(items);
                            Presenter.getInstance().saveDataToPreference("news_" + channelId, itemsString);
                            loadLisenter.onComplete(items);
                        } else {
                            loadLisenter.onError();
                        }
                    } else {
                        loadLisenter.onError();
                    }
                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }


            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).addParemeter("page", "" + page)
                .addParemeter("channelId", channelId)
                .addParemeter("needContent", "0")
                .addParemeter("needHtml", "0")
                .addParemeter("needAllList", "0")
                .addParemeter("maxResult", "10")
                .post(queue);
        page = page > allpage ? 1 : ++page;
        storePage();
    }

    /**
     * 新闻频道获取
     *
     * @param loadLisenter
     */
    public void getNewsChannel(final NewsLoadLisenter<Channel> loadLisenter) {
        if (!Presenter.getInstance().checkNetStat()) {
            loadLisenter.onError();
            return;
        }
        new ApiRequest(Api.GET_CHANNEL_API, new ApiRequest.Listener() {
            @Override
            public void onResponse(String response) {
                response = response.replaceAll("焦点", "");
                try {
//                    ApiRespond<ChannelRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<ChannelRespondBody>>() {
//                    });
                    ApiRespond<ChannelRespondBody> apiRespond=ApiRespond.parserJsonString(response ,new TypeToken<ApiRespond<ChannelRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0) {

                        ChannelRespondBody body = apiRespond.getShowapi_res_body();
                        List<Channel> channels = body.getChannelList();
                        if (channels != null && channels.size() > 0) {
                            String channelsJsonString = gson.toJson(channels);
                            Presenter.getInstance().saveDataToPreference("newChannels", channelsJsonString);
                            List<Channel> userChannels = new ArrayList<>();
                            for (int i = 0; i < 10; i++) {
                                userChannels.add(channels.get(i));
                            }
//                            String userString = JSON.toJSONString(userChannels);
                            String userString =gson.toJson(userChannels);;
                            Presenter.getInstance().saveDataToPreference("userNewsChannels", userString);
                            loadLisenter.onComplete(channels);
                        } else {
                            loadLisenter.onError();
                        }

                    } else {
                        loadLisenter.onError();
                    }
                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }


            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).post(queue);


    }

    /**
     * 图片获取
     *
     * @param loadLisenter
     */
    public void getPicture(final NewsLoadLisenter<Picture> loadLisenter) {
        new ApiRequest(Api.GET_PIC_API, new ApiRequest.Listener() {
            @Override
            public void onResponse(String response) {

                try {
//                    ApiRespond<PictureRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<PictureRespondBody>>() {
//                    });
                    ApiRespond<PictureRespondBody> apiRespond=ApiRespond.parserJsonString(response,new TypeToken<ApiRespond<PictureRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0) {
                        PictureRespondBody body = apiRespond.getShowapi_res_body();
                        if (body.getCode().equals("200")) {
                            List<Picture> pictures = body.getNewslist();
                            for (Picture picture : pictures) {
                                System.out.println(picture.getPicUrl());
                            }
                            loadLisenter.onComplete(pictures);

                        } else {
                            loadLisenter.onError();
                        }

                    } else {
                        loadLisenter.onError();
                    }
                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }


            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).addParemeter("num", "10")//数量
                .addParemeter("rand", "1")//随机
                .post(queue);


    }

    /**
     * 新闻内容抽取
     *
     * @param url          新闻网址
     * @param loadLisenter
     */
    public void getNewsContent(String url, final LoadLisenter<NewsContentRespondBody> loadLisenter) {
        new ApiRequest(Api.GET_CONTENTOFNEWS_API, new ApiRequest.Listener() {

            @Override
            public void onResponse(String response) {

                try {
//                    ApiRespond<NewsContentRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<NewsContentRespondBody>>() {
//                    });
                    ApiRespond<NewsContentRespondBody> apiRespond=ApiRespond.parserJsonString(response,new TypeToken<ApiRespond<NewsContentRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0) {

                        loadLisenter.onComplete(apiRespond.getShowapi_res_body());
                    } else {
                        loadLisenter.onError();
                    }
                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).addParemeter("url", url)
                .post(queue);


    }

    /**
     * 快递查询
     *
     * @param nu           单号
     * @param loadLisenter 监听
     */
    public void getExpressData(String nu, final LoadLisenter<ExpressRespondBody> loadLisenter) {
        //Api.GET_EXP_API
        new ApiRequest(Api.GET_EXP_API, new ApiRequest.Listener() {

            @Override
            public void onResponse(String response) {
                // System.out.println(response);
                try {
//                    ApiRespond<ExpressRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<ExpressRespondBody>>() {
//                    });
                    ApiRespond<ExpressRespondBody> apiRespond=ApiRespond.parserJsonString(response,new TypeToken<ApiRespond<ExpressRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0 && "000".equals(apiRespond.getShowapi_res_body().getError_code())) {

                        loadLisenter.onComplete(apiRespond.getShowapi_res_body());
                    } else {
                        loadLisenter.onError();
                    }
                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).addParemeter("nu", nu)
                .post(queue);


    }

    public void setPage(int page) {
        this.page = page;
    }

    private void storePage() {
        Presenter.getInstance().pageMark(page);
    }

    public void getWeather(String area, final LoadLisenter<WeatherRespondBody> loadLisenter) {
        getWeather(area,loadLisenter,true);
    }
    public void getWeather(String area, final LoadLisenter<WeatherRespondBody> loadLisenter,boolean flag) {
        if (flag&&!Presenter.getInstance().checkNetStat()) {
            String weather = Presenter.getInstance().getDataFromPerference("weather", "");
            if (!weather.equals("")) {
//                ApiRespond<WeatherRespondBody> apiRespond = JSON.parseObject(weather, new TypeReference<ApiRespond<WeatherRespondBody>>() {
//                });
                ApiRespond<WeatherRespondBody> apiRespond=ApiRespond.parserJsonString(weather,new TypeToken<ApiRespond<WeatherRespondBody>>(){});
                loadLisenter.onComplete(apiRespond.getShowapi_res_body());
            } else {
                loadLisenter.onError();
            }
            return;
        }
        new ApiRequest(Api.GET_WEATHER_BY_EAR_API, new ApiRequest.Listener() {

            @Override
            public void onResponse(String response) {
                // System.out.println(response);
                try {
//                    ApiRespond<WeatherRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<WeatherRespondBody>>() {
//                    });
                    ApiRespond<WeatherRespondBody> apiRespond=ApiRespond.parserJsonString(response,new TypeToken<ApiRespond<WeatherRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0) {
                        WeatherRespondBody weatherRespondBody = apiRespond.getShowapi_res_body();
                        if (weatherRespondBody.getRet_code() == 0) {
                            Presenter.getInstance().saveDataToPreference("weather", response);
                            Presenter.getInstance().saveDataToPreference("weatherUpdataTime", Util.getCurrentTime());
                            loadLisenter.onComplete(weatherRespondBody);
                        } else if (weatherRespondBody.getRet_code() == -1) {
                            Presenter.getInstance().showToast("选择的城市错误！将自动获取本地天气信息");
                            getWeather(loadLisenter);

                        } else {
                            loadLisenter.onError();
                        }


                    } else {
                        loadLisenter.onError();

                    }

                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).addParemeter("area", area)
                .addParemeter("needIndex", "1")
                .addParemeter("needMoreDay", "1")
                .post(queue);

    }

    public void getWeather(final LoadLisenter<WeatherRespondBody> loadLisenter) {
        if (!Presenter.getInstance().checkNetStat()){
            String weather = Presenter.getInstance().getDataFromPerference("weather", "");
            if (!weather.equals("")) {
//                ApiRespond<WeatherRespondBody> apiRespond = JSON.parseObject(weather, new TypeReference<ApiRespond<WeatherRespondBody>>() {
//                });
                ApiRespond<WeatherRespondBody> apiRespond=ApiRespond.parserJsonString(weather,new TypeToken<ApiRespond<WeatherRespondBody>>(){});
                loadLisenter.onComplete(apiRespond.getShowapi_res_body());
            } else {
                loadLisenter.onError();
            }
            return;
        }
        new ApiRequest(Api.GET_WEATHER_BY_IP_API, new ApiRequest.Listener() {

            @Override
            public void onResponse(String response) {
                try {
//                    ApiRespond<WeatherRespondBody> apiRespond = JSON.parseObject(response, new TypeReference<ApiRespond<WeatherRespondBody>>() {
//                    });
                    ApiRespond<WeatherRespondBody> apiRespond=ApiRespond.parserJsonString(response,new TypeToken<ApiRespond<WeatherRespondBody>>(){});
                    if (apiRespond.getShowapi_res_code() == 0) {
                        WeatherRespondBody weatherRespondBody = apiRespond.getShowapi_res_body();
                        if (weatherRespondBody.getRet_code() == 0) {
                            Presenter.getInstance().saveDataToPreference("weather", response);
                            Presenter.getInstance().saveDataToPreference("weatherUpdataTime", Util.getCurrentTime());
                            loadLisenter.onComplete(weatherRespondBody);
                        } else {
                            loadLisenter.onError();
                        }

                    } else {
                        loadLisenter.onError();

                    }

                } catch (Exception e) {
                    loadLisenter.onError();
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                loadLisenter.onError();
            }
        }).addParemeter("needMoreDay", "1").addParemeter("needIndex", "1").post(queue);


    }


}
