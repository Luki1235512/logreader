package com.company;

public class LogsSeverity {

    private int TRACE_cnt = 0;
    private int DEBUG_cnt = 0;
    private int INFO_cnt = 0;
    private int WARN_cnt = 0;
    private int ERROR_cnt = 0;
    private int FATAL_cnt = 0;

    public void count(String log) {
        switch (log) {
            case "TRACE": {
                this.TRACE_cnt++;
                break;
            }
            case "DEBUG": {
                this.DEBUG_cnt++;
                break;
            }
            case "INFO": {
                this.INFO_cnt++;
                break;
            }
            case "WARN": {
                this.WARN_cnt++;
                break;
            }
            case "ERROR": {
                this.ERROR_cnt++;
                break;
            }
            case "FATAL": {
                this.FATAL_cnt++;
                break;
            }
        }
    }

    public void logRatio() {
        int SEVERITY_all = (this.TRACE_cnt + this.DEBUG_cnt + this.INFO_cnt + this.WARN_cnt + this.ERROR_cnt + this.FATAL_cnt);
        int ERROR_higher = (this.ERROR_cnt + this.FATAL_cnt);
        System.out.println("Log ratio(ERROR or higher to all): " + ERROR_higher + ":" + SEVERITY_all);
    }

    @Override
    public String toString() {
        return "Logs Severity:\n" +
                "TRACE: " + TRACE_cnt +
                "\nDEBUG: " + DEBUG_cnt +
                "\nINFO: " + INFO_cnt +
                "\nWARN: " + WARN_cnt +
                "\nERROR: " + ERROR_cnt +
                "\nFATAL: " + FATAL_cnt;
    }
}
