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
import java.net.URL;
import java.util.Objects;

public class AppFrame implements ActionListener {

    JFrame frame;
    JButton button;
    JTextField from, to;
    JComboBox<String>fromCurrency, toCurrency;
    JLabel ok,ok2, header;
    String[]currencies  = {"USD","AZN","TRY","RUB","GBP","EUR"};


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
            String api="http://localhost:8080/rest/api/exchange/currencies";
            URL url=new URL(api);
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
            System.out.println(stringBuilder.toString());
            ObjectMapper objectMapper=new ObjectMapper();

            JsonNode node =objectMapper.readTree(stringBuilder.toString());


            if (node.isArray()|| !node.isEmpty()) {
                Currency currency=new Currency();

                currency.setAzn(node.get("azn").asDouble());
                currency.setEur(node.get("eur").asDouble());
                currency.setGbp(node.get("gbp").asDouble());
                currency.setUsd(node.get("usd").asDouble());
                currency.setRub(node.get("rub").asDouble());
                currency.setTrl(node.get("try").asDouble());
               Calculate(currency);
            }



        } catch (IOException e) {
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

        double amount = Double.parseDouble(from.getText()); // Kullanıcının girdiği miktar

        switch ((String) Objects.requireNonNull(fromCurrency.getSelectedItem())) {
            case "USD":
                switch ((String) Objects.requireNonNull(toCurrency.getSelectedItem())) {
                    case "AZN": to.setText(String.format("%.2f", currency.getAzn() * amount)); break;
                    case "TRY": to.setText(String.format("%.2f", currency.getTrl() * amount)); break;
                    case "EUR": to.setText(String.format("%.2f", currency.getEur() * amount)); break;
                    case "RUB": to.setText(String.format("%.2f", currency.getRub() * amount)); break;
                    case "GBP": to.setText(String.format("%.2f", currency.getGbp() * amount)); break;
                }
                break;

            case "AZN":
                switch ((String) Objects.requireNonNull(toCurrency.getSelectedItem())) {
                    case "USD": to.setText(String.format("%.2f", amount / currency.getAzn())); break;
                    case "TRY": to.setText(String.format("%.2f", currency.getTrl() / currency.getAzn() * amount)); break;
                    case "EUR": to.setText(String.format("%.2f", currency.getEur() / currency.getAzn() * amount)); break;
                    case "RUB": to.setText(String.format("%.2f", currency.getRub() / currency.getAzn() * amount)); break;
                    case "GBP": to.setText(String.format("%.2f", currency.getGbp() / currency.getAzn() * amount)); break;
                }
                break;

            case "TRY":
                switch ((String) Objects.requireNonNull(toCurrency.getSelectedItem())) {
                    case "USD": to.setText(String.format("%.2f", amount / currency.getTrl())); break;
                    case "AZN": to.setText(String.format("%.2f", currency.getAzn() / currency.getTrl() * amount)); break;
                    case "EUR": to.setText(String.format("%.2f", currency.getEur() / currency.getTrl() * amount)); break;
                    case "RUB": to.setText(String.format("%.2f", currency.getRub() / currency.getTrl() * amount)); break;
                    case "GBP": to.setText(String.format("%.2f", currency.getGbp() / currency.getTrl() * amount)); break;
                }
                break;

            case "EUR":
                switch ((String) Objects.requireNonNull(toCurrency.getSelectedItem())) {
                    case "USD": to.setText(String.format("%.2f", amount / currency.getEur())); break;
                    case "TRY": to.setText(String.format("%.2f", currency.getTrl() / currency.getEur() * amount)); break;
                    case "AZN": to.setText(String.format("%.2f", currency.getAzn() / currency.getEur() * amount)); break;
                    case "RUB": to.setText(String.format("%.2f", currency.getRub() / currency.getEur() * amount)); break;
                    case "GBP": to.setText(String.format("%.2f", currency.getGbp() / currency.getEur() * amount)); break;
                }
                break;

            case "RUB":
                switch ((String) Objects.requireNonNull(toCurrency.getSelectedItem())) {
                    case "USD": to.setText(String.format("%.2f", amount / currency.getRub())); break;
                    case "TRY": to.setText(String.format("%.2f", currency.getTrl() / currency.getRub() * amount)); break;
                    case "EUR": to.setText(String.format("%.2f", currency.getEur() / currency.getRub() * amount)); break;
                    case "AZN": to.setText(String.format("%.2f", currency.getAzn() / currency.getRub() * amount)); break;
                    case "GBP": to.setText(String.format("%.2f", currency.getGbp() / currency.getRub() * amount)); break;
                }
                break;

            case "GBP":
                switch ((String) Objects.requireNonNull(toCurrency.getSelectedItem())) {
                    case "USD": to.setText(String.format("%.2f", amount / currency.getGbp())); break;
                    case "TRY": to.setText(String.format("%.2f", currency.getTrl() / currency.getGbp() * amount)); break;
                    case "EUR": to.setText(String.format("%.2f", currency.getEur() / currency.getGbp() * amount)); break;
                    case "RUB": to.setText(String.format("%.2f", currency.getRub() / currency.getGbp() * amount)); break;
                    case "AZN": to.setText(String.format("%.2f", currency.getAzn() / currency.getGbp() * amount)); break;
                }
                break;

            default:
                to.setText("Hatalı seçim!");
                break;
        }


    }
}
