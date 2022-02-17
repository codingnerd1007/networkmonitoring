package com.motadata.traceorg.bean;

public class NmsDashboardBean {
    private String total;
    private String active;
    private String unknown;
    private String inactive;

    public String getTotal() {
        return total;
    }

    public String getActive() {
        return active;
    }

    public String getUnknown() {
        return unknown;
    }

    public String getInactive() {
        return inactive;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setUnknown(String unknown) {
        this.unknown = unknown;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }
}
