import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main{
    static double totalMoney = 10000;
    static int numberOfShares = 0;
    static double currentPriceOfShare = 0;

    static void trade(int x) {
        if ((totalMoney > (currentPriceOfShare * x)) || x < 0) {
            if (x > 0) {
                totalMoney = totalMoney - (currentPriceOfShare * x);
                numberOfShares = numberOfShares + x;
            } else if (-x > numberOfShares) {
                totalMoney = totalMoney + (currentPriceOfShare * numberOfShares);
                numberOfShares = 0;
            } else {
                totalMoney = totalMoney - (currentPriceOfShare * x);
                numberOfShares = numberOfShares + x;
            }
        } else {
            int tradingNumber = (int) Math.floor(totalMoney / currentPriceOfShare);
            numberOfShares += tradingNumber;
            totalMoney = totalMoney - (currentPriceOfShare * tradingNumber);
        }
    }

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("stocks.txt"), true);
        double dailyProfit;
        double totalProfit;
        double lastAmount = 10000;
        double currentAmount;

        for (int i = 1; i < 150; i++) {
            //Creating fuzzy sets according to ranges
            FuzzySet mad_negative = new FuzzySet(0.0, 0.4);
            FuzzySet mad_zero = new FuzzySet(0.3, 0.7);
            FuzzySet mad_positive = new FuzzySet(0.6, 1.0);
            FuzzySet price_very_low = new FuzzySet(0.0, 0.3);
            FuzzySet price_low = new FuzzySet(0.25, 0.5);
            FuzzySet price_medium = new FuzzySet(0.4, 0.6);
            FuzzySet price_high = new FuzzySet(0.5, 0.75);
            FuzzySet price_very_high = new FuzzySet(0.7, 1.0);
            FuzzySet sell_many = new FuzzySet(0.0, 0.4);
            FuzzySet sell_few = new FuzzySet(0.3, 0.5);
            FuzzySet do_not_trade = new FuzzySet(0.4, 0.6);
            FuzzySet buy_few = new FuzzySet(0.5, 0.7);
            FuzzySet buy_many = new FuzzySet(0.6, 1.0);
            double[] weights = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            //Normalizing price values in the range of 0-1.
            double priceValue = FuzzyUtils.normalizePrice(FuzzyUtils.price(i));
            double madValue = FuzzyUtils.normalizeMad(FuzzyUtils.mad(i));
            currentPriceOfShare = FuzzyUtils.price(i);

            //Setting rules...
            //The respective fuzzy sets are cut at the minimum point of their antecedents using the condition of and-ing
            //So the following lines translate to (if(mad is positive) and (price is low)) Then  buy_many
            buy_many.slice(Math.min(mad_positive.membership(madValue), price_very_low.membership(priceValue)));
            //(if(mad is positive) and (price is very low)) Then  buy_few
            buy_few.slice(Math.min(mad_positive.membership(madValue), price_low.membership(priceValue)));
            //(if(mad is positive) and (price is medium)) Then  DNT
            do_not_trade.slice(Math.min(mad_positive.membership(madValue), price_medium.membership(priceValue)));
            //(if(mad is positive) and (price is high)) Then  DNT
            do_not_trade.slice(Math.min(mad_positive.membership(madValue), price_high.membership(priceValue)));
            //(if(mad is positive) and (price is very high)) Then  DNT
            do_not_trade.slice(Math.min(mad_positive.membership(madValue), price_very_high.membership(priceValue)));
            //(if(mad is zero) and (price is very low)) Then  buy_many
            buy_many.slice(Math.min(mad_zero.membership(madValue), price_very_low.membership(priceValue)));
            //(if(mad is zero) and (price is low)) Then  buy_few
            buy_few.slice(Math.min(mad_zero.membership(madValue), price_low.membership(priceValue)));
            //(if(mad is zero) and (price is medium)) Then  DNT
            do_not_trade.slice(Math.min(mad_zero.membership(madValue), price_medium.membership(priceValue)));
            //(if(mad is zero) and (price is high)) Then  sell_few
            sell_few.slice(Math.min(mad_zero.membership(madValue), price_high.membership(priceValue)));
            //(if(mad is zero) and (price is very high)) Then  sell_many
            sell_many.slice(Math.min(mad_zero.membership(madValue), price_very_high.membership(priceValue)));
            //(if(mad is negative) and (price is very low)) Then  buy_many
            buy_many.slice(Math.min(mad_negative.membership(madValue), price_very_low.membership(priceValue)));
            //(if(mad is negative) and (price is low)) Then  buy_few
            buy_few.slice(Math.min(mad_negative.membership(madValue), price_low.membership(priceValue)));
            //(if(mad is negative) and (price is medium)) Then  sell_few
            sell_few.slice(Math.min(mad_negative.membership(madValue), price_medium.membership(priceValue)));
            //(if(mad is negative) and (price is high)) Then  sell_many
            sell_many.slice(Math.min(mad_negative.membership(madValue), price_high.membership(priceValue)));
            //(if(mad is negative) and (price is very high)) Then  sell_many
            sell_many.slice(Math.min(mad_negative.membership(madValue), price_very_high.membership(priceValue)));

            //Initializing the weights of the array according to the respective fuzzy sets
            for (int j = 0; j < 11; j++) {
                weights[j] = weights[j] + sell_many.membership((double) j / 10);
                weights[j] = weights[j] + sell_few.membership((double) j / 10);
                weights[j] = weights[j] + do_not_trade.membership((double) j / 10);
                weights[j] = weights[j] + buy_few.membership((double) j / 10);
                weights[j] = weights[j] + buy_many.membership((double) j / 10);
            }

            double cog = (weights[1] * 0.1) +
                    (weights[2] * 0.2) +
                    (weights[3] * 0.3) +
                    (weights[4] * 0.4) +
                    (weights[5] * 0.5) +
                    (weights[6] * 0.6) +
                    (weights[7] * 0.7) +
                    (weights[8] * 0.8) +
                    (weights[9] * 0.9) +
                    (weights[10] * 1.0);

            cog = cog / (weights[0] +
                    weights[1] +
                    weights[2] +
                    weights[3] +
                    weights[4] +
                    weights[5] +
                    weights[6] +
                    weights[7] +
                    weights[8] +
                    weights[9] +
                    weights[10]);

            //Actual trading of shares
            trade(FuzzyUtils.defuzzifyShares(cog));

            currentAmount = (totalMoney + (currentPriceOfShare * numberOfShares));
            dailyProfit = currentAmount - lastAmount;
            lastAmount = currentAmount;
            totalProfit = ((totalMoney + (currentPriceOfShare * numberOfShares)) - 10000);

            // Print results out to file
            out.printf("Day: %4d" +
                    "\t\tSuggestion: %4d" +
                    "\t\t Price Of Share: %4.2f" +
                    "\t\t Total Money: %10.2f" +
                    "\t\t Number of Shares: %6d" +
                    "\t\t Total Asset: %10.2f" +
                    "\t\t Daily Profit: %10.2f" +
                    "\t\t Total Profit: %10.2f\n",
                    i,
                    FuzzyUtils.defuzzifyShares(cog),
                    currentPriceOfShare,
                    totalMoney,
                    numberOfShares,
                    currentAmount,
                    dailyProfit,
                    totalProfit);
        }
        out.close();
    }
}
