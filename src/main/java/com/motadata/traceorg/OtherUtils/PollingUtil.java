package com.motadata.traceorg.OtherUtils;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class PollingUtil
{
    private static final LinkedBlockingQueue<HashMap<String,String>> pollRequestQueue=new LinkedBlockingQueue<>();
    private static final Thread[] threads=new Thread[16];
    public static void initPollingThreads()
    {
        for(int iterator=0;iterator<16;iterator++)
        {
            threads[iterator]=new Thread(new PollingExec());
            threads[iterator].start();
        }
    }
    public static void putPollRequest(HashMap<String,String> request)
    {
        pollRequestQueue.add(request);
    }
    public static HashMap<String,String> takePollRequest()
    {
        try
        {
           return pollRequestQueue.take();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }
}
