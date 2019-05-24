package pkgUT;

import app.LoanResolver;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestCalculate {
    @Test
    public void testCalculate(){
        double exptp=10831.25;
        double expti=2831.25;
        double loanAmount=8000.00;
        double interestRate=0.6;
        int termOfYears=1;
        LocalDate firstPaymentDate= LocalDate.of(2018,5,1);
        double additionalPayment=0.00;

        double expp[]={
                902.60,902.60,902.60,902.60,902.60,902.60,902.60,902.60,902.60,902.60,902.60,902.60,0.05
        };
        double expi[]={
                400.00,374.87,348.48,320.78,291.69,261.14,229.07,195.39,160.03,122.90,83.92,42.98,0.00
        };
       double exppp[]={
               502.60,527.73,554.12,581.82,610.91,641.46,673.53,707.21,742.57,779.70,818.68,859.62,0.05
       };
       double expbp[]={
               7497.40,6969.67,6415.55,5833.73,5222.82,4581.36,3907.83,3200.62,2458.05,1678.35,859.67,0.05,0.00
       };


        LoanResolver lr = new LoanResolver();
        ObservableList<LoanResolver.PaymentItem> data
                = lr.CalculatePayment(loanAmount,interestRate,termOfYears,additionalPayment,firstPaymentDate);

        for(int i=1;i<data.size();++i){
            assertEquals(Double.parseDouble(data.get(i).getPayment()),expp[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getInterest()),expi[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getPrinciple()),exppp[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getBalance()),expbp[i-1],0.01);
        }
        assertEquals(expti,lr.getTotalInterests(),0.01);
        assertEquals(exptp,lr.getTotalPayments(),0.01);
    }
}
