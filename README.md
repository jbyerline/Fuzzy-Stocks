# CS657_Assignment_2 - Fuzzy Stocks - Jacob Byerline

## Problem Description:
The problem at hand is to develop a fuzzy system that can estimate whether a stock price will rise or fall in the following day. Based on this value, we can determine how many, if any, stock to buy or sell per day in an attempt to maximize our profit. For this program, we start off with $10,000.00. The goal is to end the simulation with more money than you started with. 

Our stock prices are determined by the following formula:
P(i) = 10 + 2.8 sin⁡(2π/19) + 0.9 cos⁡(2π/19) + ζ(i)
Our MAD indicator is determined by the following formula:
m(i) = 0.6 cos(0.4i) - sin(0.5i) + η(i)

## My Approach:
I decided to create this program in Java. At a high level, I broke this program down into 3 distinct classes. They are as follows:

	Main
	FuzzySet
	FuzzyUtils

Starting from the top of the class list, the Main class serves as an entry point of this program. It instantiates 150 FuzzySets and then uses those values in the given rules to determine whether or not we should perform a given action. Once we determine which actions we should perform, we can weigh our options and then perform a trade. Once the trade is performed, we append those results to our stocks.txt file and try again for the next day, until all 150 days are traded. 

The next class is the FuzzySet class. This class contains two methods and helps us determine the membership as well as where to draw the line between two rules. These methods are the Slice() and Membership() methods.

Next, we have the FuzzyUtils class. As in the name, this class contains utility helper functions for the driver class. We have the price method which uses the formula above to determine a stock price. The MAD method which uses the other formula above to determine the mad indicator. Then we have normalization methods for both of those two vales, the price and MAD. Finally, we have a roundToDouble method for rounding numbers and a defuzzification method for, as it is named, defuzzification. 

## Rules:
	if(mad is positive) and (price is very low)) Then  buy_few
	if(mad is positive) and (price is low)) Then  buy_many
	if(mad is positive) and (price is medium)) Then  DNT
	if(mad is positive) and (price is high)) Then  DNT
	if(mad is positive) and (price is very high)) Then  DNT
	if(mad is zero) and (price is very low)) Then  buy_many
	if(mad is zero) and (price is low)) Then  buy_few
	if(mad is zero) and (price is medium)) Then  DNT
	if(mad is zero) and (price is high)) Then  sell_few
	if(mad is zero) and (price is very high)) Then  sell_many
	if(mad is negative) and (price is very low)) Then  buy_many
	if(mad is negative) and (price is low)) Then  buy_few
	if(mad is negative) and (price is medium)) Then  sell_few
	if(mad is negative) and (price is high)) Then  sell_many
	if(mad is negative) and (price is very high)) Then  sell_many

## Notable Additions:
The stocks.txt file contains a lot of additional metrics about the stock prices including the day, suggestion, price of share, total money, number of shares, total assets, daily profit, and total profit. 

## Running Instructions:
This program was written with Java 14.0.1. The executable jar file can be run using the command java -jar “Program 2.jar” on MacOS. Please keep in mind, to execute a jar file, you need to have the same version of Java installed on your system. Otherwise, you can compile the source code using your local java by opening the code in and IDE and executing the main method. 

Once the program has started it will run based on randomly generated numbers and produce a result. You can review the output in the stocks.txt file. 

## Other Work

You can find other amazing projects in my [GitHub Repo](https://github.com/jbyerline)
