package Listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.SoftAssertionUtil;

public  class FailRetry implements IRetryAnalyzer
{
    int retry;
    int maxRetry=2;

    @Override
    public boolean retry(ITestResult result)
    {
        if(retry<maxRetry)
        {
            retry++;

            return true;

        }
        return false;
    }

}
