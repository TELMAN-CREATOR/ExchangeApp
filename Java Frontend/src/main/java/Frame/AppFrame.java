package Frame;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class AppFrame implements ActionListener {

    JFrame frame;
    JButton button;
    JTextField from, to;
    JComboBox<String>fromCurrency, toCurrency;
    JLabel ok,ok2, header;
    String[]currencies  = {"AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
                           "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL",
                           "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY",
                           "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP",
                           "ERN", "ETB", "EUR", "FJD", "FKP", "FOK", "GBP", "GEL", "GGP", "GHS",
                           "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF",
                           "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD",
                           "JPY", "KES", "KGS", "KHR", "KID", "KMF", "KRW", "KWD", "KYD", "KZT",
                           "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD",
                           "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN",
                           "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK",
                           "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR",
                           "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS", "SRD",
                           "SSP", "STN", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY",
                           "TTD", "TVD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VES",
                           "VND", "VUV", "WST", "XAF", "XCD", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"};


    AppFrame(){
        frame=new JFrame();
        button=new JButton("Change");
        from=new JTextField();
        to=new JTextField();
        ok=new JLabel("==>>");
        ok2=new JLabel("==>>");
        header=new JLabel("Exchange");
        fromCurrency=new JComboBox<>(currencies);
        toCurrency=new JComboBox<>(currencies);

        frame.add(header);
        frame.add(ok);
        frame.add(ok2);
        frame.add(button);
        frame.add(from);
        frame.add(to);
        frame.add(fromCurrency);
        frame.add(toCurrency);

        to.setEditable(false);
        header.setBounds(160,80,200,50);
        header.setFont(new Font("Plain",Font.BOLD,30));
        fromCurrency.setBounds(100,160,100,50);
        toCurrency.setBounds(300,160,100,50);
        ok.setBounds(230,160,50,50);
        ok2.setBounds(230,250,50,50);
        from.setBounds(100,250,100,50);
        to.setBounds(300,250,100,50);
        button.setBounds(100,350,300,50);
        button.addActionListener(this);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setSize(500,500);
        frame.setVisible(true);

    }






    public static void main(String[] args) {
        new AppFrame();
    }

    public void getCurrency(){


        try {
            String api="http://localhost:8080/rest/api/exchange/";
            URI uri=new URI(api+(String)(fromCurrency.getSelectedItem())+"/"+(String)toCurrency.getSelectedItem());
            URL url=uri.toURL();
            HttpURLConnection get=(HttpURLConnection) url.openConnection();
            get.setRequestMethod("GET");

            BufferedReader buffered=new BufferedReader(new InputStreamReader(get.getInputStream()));

            String line;
            StringBuilder stringBuilder=new StringBuilder();
            while ((line= buffered.readLine())!=null){
                stringBuilder.append(line);
            }

            buffered.close();
            get.disconnect();

            ObjectMapper objectMapper=new ObjectMapper();

            JsonNode node =objectMapper.readTree(stringBuilder.toString());

            System.out.println("API Response: " + stringBuilder.toString());

            Currency currency=new Currency();

            JsonNode currencyNode = node.get("curremcy");
            if (currencyNode != null) {
                currency.setCurremcy(currencyNode.asDouble());
                Calculate(currency);
            } else {
                System.out.println("Currency not found in the API response.");
            }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }




    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==button){
            getCurrency();


        }

    }

    private void Calculate(Currency currency){

        try {
            double amount = Double.parseDouble(from.getText());
            to.setText(String.format("%.2f", currency.getCurremcy() * amount));
            if (amount<=0)
                throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
        }


    }
}
