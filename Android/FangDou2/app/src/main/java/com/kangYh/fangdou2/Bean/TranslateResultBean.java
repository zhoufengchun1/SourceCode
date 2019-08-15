package com.kangYh.fangdou2.Bean;

import java.util.List;

public class TranslateResultBean
{

    private String from;
    private String to;
    private List<ResultPairBean> trans_result;

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public List<ResultPairBean> getTrans_result()
    {
        return trans_result;
    }

    public void setTrans_result(List<ResultPairBean> trans_result)
    {
        this.trans_result = trans_result;
    }
}
