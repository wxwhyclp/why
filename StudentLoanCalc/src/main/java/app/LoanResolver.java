package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.time.LocalDate;

public class LoanResolver {
    private final ObservableList<PaymentItem> data = FXCollections.observableArrayList();
    private double totalPayments;
    private double totalInterests;

    public double getTotalPayments() {
        totalPayments=0;
        for(int i=1;i<data.size();++i){
            totalPayments+=Double.parseDouble(data.get(i).getPayment());
        }
        totalPayments=rsecond(totalPayments);
        return totalPayments;
    }

    public double getTotalInterests() {
        totalInterests=0;
        for(int i=1;i<data.size();++i){
            totalInterests+=Double.parseDouble(data.get(i).getInterest());
        }
        totalInterests=rsecond(totalInterests);
        return totalInterests;
    }
    
    public static double rsecond(double value) {
        return ((double)Math.round(value*100))/100;
    }
    
    public ObservableList<PaymentItem> CalculatePayment(double loanAmount, double interestRate, double numberOfYears, double additionalPayment, LocalDate date){
        int payn=0;
        double balance=rsecond(loanAmount-additionalPayment);
        double twelvey = numberOfYears*12.00;
		double iperm = interestRate/12.00;
		double PMT = rsecond(Math.abs(FinanceLib.pmt(iperm, twelvey, loanAmount, 0, false)));
        data.add(new PaymentItem(null,null,null,null,null,null,String.format("%.2f",balance)));


        while(balance>0){
            double interest=rsecond(balance*interestRate/12.00);
            double principal;
            double payment;
            if(rsecond(PMT-(balance+interest))>=-0.01){
                principal=balance;
                payment=rsecond(balance+interest);
                balance=0;
            }else{
                principal=rsecond(PMT-interest);
                payment=PMT;
                balance=rsecond(balance-principal);
            }
            payn=payn + 1;
            data.add(
                    new PaymentItem(Integer.toString(payn),date.toString(),String.format("%.2f",payment),null,String.format("%.2f",interest),String.format("%.2f",principal),String.format("%.2f",balance)));
            date=date.plusMonths(1);
        }
        return data;
    }



    public static class PaymentItem {
        private final SimpleStringProperty pn;
        private final SimpleStringProperty DATE;
        private final SimpleStringProperty payment;
        private final SimpleStringProperty AP;
        private final SimpleStringProperty INTEREST;
        private final SimpleStringProperty principle;
        private final SimpleStringProperty balance;

        public PaymentItem(String paymentNumber, String date, String payment, String additionalPayment, String interest, String principle, String balance) {
            this.pn = new SimpleStringProperty(paymentNumber);
            this.DATE = new SimpleStringProperty(date);
            this.payment = new SimpleStringProperty(payment);
            this.AP = new SimpleStringProperty(additionalPayment);
            this.INTEREST = new SimpleStringProperty(interest);
            this.principle = new SimpleStringProperty(principle);
            this.balance = new SimpleStringProperty(balance);
        }

        public String getPaymentNumber() {
            return this.pn.get();
        }

        public void setPaymentNumber(String paymentNumber) {
            this.pn.set(paymentNumber);
        }

        public String getDate() {
            return this.DATE.get();
        }

        public void setDate(String date) {
            this.DATE.set(date);
        }

        public String getPayment() {
            return this.payment.get();
        }

        public void setPayment(String payment) {
            this.payment.set(payment);
        }

        public String getAdditionalPayment() {
            return this.AP.get();
        }

        public void setAdditionalPayment(String additionalPayment) {
            this.AP.set(additionalPayment);
        }

        public String getInterest() {
            return this.INTEREST.get();
        }

        public void setInterest(String interest) {
            this.INTEREST.set(interest);
        }

        public String getPrinciple() {
            return this.principle.get();
        }

        public void setPrinciple(String principle) {
            this.principle.set(principle);
        }

        public String getBalance() {
            return this.balance.get();
        }

        public void setBalance(String balance) {
            this.balance.set(balance);
        }
    }
}
