package com.chrisbarbati.weatherserver.RPI;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

/**
 * Christian Barbati - Dec 2023
 *
 * Class to add methods that make it easier to interact with
 * the Raspberry Pi Sense Hat
 */

public class SenseHat
{
    /**
     * I2C _addresses of the various chips on the Sense HAT
     */
    static final int LPS25H_ADDRESS = 0x5c; //Pressure (temp)
    static final int HTS221_ADDRESS = 0x5f; //Humidity (temp)
    static final int LSM9DS1_ADDRESS = 0x1c; //Accelerometer. Docs also show 0x6a, test later
    static final int LED2472G_ADDRESS = 0x46; //ATTiny88 (LEDs, joystick, colour sensor(?))

    /**
     * I2C registers where data is read from the chips
     */
    static final int LPS25H_TEMP_OUT_H_REGISTER = 0x2c;
    static final int LPS25H_TEMP_OUT_L_REGISTER = 0x2b;

    static final int LPS25H_PRESS_OUT_H_REGISTER = 0x2A;
    static final int LPS25H_PRESS_OUT_L_REGISTER = 0x29;
    static final int LPS25H_PRESS_OUT_XL_REGISTER = 0x28;



    public static void main( String[] args )
    {

        System.out.println("Current Temperature (C): " + getTempFromPressure());
        System.out.println("Current Temperature (C) from humidity: " + getTempFromHumidity());
        System.out.println("Current Temperature (Averaged): " + getTemperatureAveraged());
        System.out.println("Current Pressure (mbar): " + getPressureMbar());
        System.out.println("Current Relative Humidity (%): " + getHumidity());

    }


    /**
     * Returns a double representing the current temperature reading in degrees Celsius, as read by the LPS25H pressure sensor
     * @return
     */
    public static double getTempFromPressure(){
        double temperature = 0;

        I2C tempI2C = getI2C("TEMPFROMPRESSURE", LPS25H_ADDRESS);

        try  {

            if(!initializeLPS25H(tempI2C)){
                //Add proper exception-handling here later
                System.out.println("Error initializing LPS25H");
            }

            //Get the temperature readings from the registers and store them as Strings
            String tempStringHigh = Integer.toBinaryString(tempI2C.readRegister(LPS25H_TEMP_OUT_H_REGISTER));
            String tempStringLow = Integer.toBinaryString(tempI2C.readRegister(LPS25H_TEMP_OUT_L_REGISTER));

            //The readings from the registers represent 8 bit values. Add zeroes to the left hand side until the values are 8 bits
            tempStringHigh = fillEightBit(tempStringHigh);
            tempStringLow = fillEightBit(tempStringLow);

            //Concatenate the two strings to get the 16 bit value for the temperature
            String tempString = tempStringHigh + tempStringLow;

            double cycles;

            /**
             * If the leading bit is a zero, convert from twos-complement.
             *
             * Otherwise simply convert to an integer
             *
             * (Cycles is the nomenclature in LPS25H docs, so I am keeping it consistent here)
             */
            if(tempString.charAt(0) == '1'){
                cycles = fromTwosComplement(tempString);
            }else{
                cycles = Integer.parseInt(tempString, 2);
            }

            //Temperature offset is cycles/480, relative to a base number of 42.5 degrees Celsius
            temperature = 42.5 + (cycles/480);
        } catch (Exception e){
            System.out.println(e);
        }


        return temperature;
    }

    /**
     * Initializes the appropriate registers on the LPS25H
     * to enable reading temperature / pressure.
     * @param tempI2C
     * @return
     */
    public static boolean initializeLPS25H(I2C tempI2C){
        try{
            //Set CTRL_REG1. Enable output, set data rate to 25Hz, don't update output registers until MSB and LSB update
            tempI2C.writeRegister(0x20, 0xc4);
            //Set RES_CONF. Set temp internal avereage to 16, pressure internal average to 32
            tempI2C.writeRegister(0x10, 0x05);
            //Set FIFO_CTRL. Set FIFO to generate a running average filtered pressure
            tempI2C.writeRegister(0x2E, 0xc0);
            //Set CTRL_REG4. Unclear what this does in RTIMU, doing it here until I can test.
            tempI2C.writeRegister(0x23, 0x40);

            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }


    }

    /**
     * Returns a double representing the current pressure in millibar, as read by the LPS25H pressure sensor
     * @return
     */
    public static double getPressureMbar(){
        double pressure = 0;

        I2C pressureI2C = getI2C("PRESSURE", LPS25H_ADDRESS);

        if(!initializeLPS25H(pressureI2C)){
            //Add proper exception-handling here later
            System.out.println("Error initializing LPS25H");
        }

        try  {

            int pressureH = pressureI2C.readRegister(LPS25H_PRESS_OUT_H_REGISTER);
            int pressureL = pressureI2C.readRegister(LPS25H_PRESS_OUT_L_REGISTER);
            int pressureXL = pressureI2C.readRegister(LPS25H_PRESS_OUT_XL_REGISTER);

            String pressureHigh = Integer.toBinaryString(pressureH);
            String pressureLow = Integer.toBinaryString(pressureL);
            String pressureExtraLow = Integer.toBinaryString(pressureXL);

            pressureHigh = fillEightBit(pressureHigh);
            pressureLow = fillEightBit(pressureLow);
            pressureExtraLow = fillEightBit(pressureExtraLow);

            String tempString = pressureHigh + pressureLow + pressureExtraLow;

            //System.out.println(tempString);

            double cycles;

            if(tempString.charAt(0) == '1'){
                cycles = fromTwosComplement(tempString);
            }else{
                cycles = Integer.parseInt(tempString, 2);
            }

            //System.out.println(cycles);

            pressure = (cycles/4096);

            //System.out.println(temp);
        } catch (Exception e){
            System.out.println(e);
        }


        return pressure;
    }

    /**
     * Returns a double representing the current pressure in PSI, as read by the LPS25H pressure sensor
     * @return
     */
    public static double getPressurePSI(){
        return getPressureMbar() / 68.948;
    }

    public static double getTemperatureAveraged(){
        return (getTempFromPressure() + getTempFromHumidity()) / 2;
    }

    /**
     * Returns a double representing the current temperature reading in degrees Celsius, as read by the HTS221 humidity sensor
     *
     */
    public static double getTempFromHumidity(){
        double temp = 0;
        I2C tempI2C = getI2C("TEMPFROMHUMIDITY", HTS221_ADDRESS);

        try  {

            //Initialization
            //Set power status to on, BDU to non-continuous mode
            tempI2C.writeRegister(0x20, 0x84);
            //Send the "one-shot" signal
            tempI2C.writeRegister(0x21, 0x01);

            //Wait for the one-shot register to set itself back to zero, indicating a reading is ready.
            while(tempI2C.readRegister(0x21) != 0){

            }

            /**
             * Temperature readings are found by combining the following:
             * MSB T0_degC T0_degC, 0x35 bit 3, 2 concatenated to 0x32
             *
             * MSB T1_degC T1_degC, 0x35 bit 1, 0 concatenated to 0x33
             *
             */

            int msb = tempI2C.readRegister(0x35);
            String msbString = Integer.toBinaryString(msb);

            msbString = fillEightBit(msbString);

            //The MSB has an 8 bit value, but we only need two bits for each calibration value
            String msbT0 = msbString.substring(7, 8);
            String msbT1 = msbString.substring(5, 6);

            int t0Cal = tempI2C.readRegister(0x32);
            int t1Cal = tempI2C.readRegister(0x33);

            String t1CalString = Integer.toBinaryString(t1Cal);
            String t0CalString = Integer.toBinaryString(t0Cal);

            //The calibration values are 10 bits, unsigned. Concatenate the MSBs
            t0CalString = msbT0 + fillEightBit(t0CalString);
            t1CalString = msbT1 + fillEightBit(t1CalString);

            /**
             * Convert the values to doubles, and divide by 8 (datasheet indicates that the registers hold a value
             * representing 8x the actual calibration value)
             */
            double t0CalDouble = Integer.parseInt(t0CalString, 2) / 8;
            double t1CalDouble = Integer.parseInt(t1CalString, 2) / 8;

            /**
             * The values we just calculated represent the y-values of two points.
             *
             * To determine the equation of the line that describes the temperature
             * reading, we also need the x-values, represented by the T0 and T1 values
             */

            //T0 and T1 are stored in two separate registers each, as a signed 16 bit value.
            int t0High = tempI2C.readRegister(0x3D);
            int t0Low = tempI2C.readRegister(0x3C);
            int t1High = tempI2C.readRegister(0x3F);
            int t1Low = tempI2C.readRegister(0x3E);

            String t0HighString = Integer.toBinaryString(t0High);
            String t0LowString = Integer.toBinaryString(t0Low);
            String t1HighString = Integer.toBinaryString(t1High);
            String t1LowString = Integer.toBinaryString(t1Low);

            t0HighString = fillEightBit(t0HighString);
            t0LowString = fillEightBit(t0LowString);
            t1HighString = fillEightBit(t1HighString);
            t1LowString = fillEightBit(t1LowString);

            //Concatenate to obtain our 16 bit values for t0 and t1
            String t0String = t0HighString + t0LowString;
            String t1String = t1HighString + t1LowString;

            int t0 = 0;

            if(t0String.charAt(0) == '1'){
                t0 = fromTwosComplement(t0String);
            }else{
                t0 = Integer.parseInt(t0String, 2);
            }

            int t1 = 0;

            if(t1String.charAt(0) == '1'){
                t1 = fromTwosComplement(t1String);
            }else{
                t1 = Integer.parseInt(t1String, 2);
            }

            /**
             * Now that we have two points, we can calculate the slope
             * by dividing rise by run
             */
            Double slope = (t1CalDouble - t0CalDouble) / (t1 - t0);

            /**
             * And the y-intercept can be determined by isolating for it
             * in the formula (y = mx + b, b = y - mx)
             */
            Double b = t1CalDouble - (slope * t1);

            /**
             * The value in TOUT represents the independent variable for the above line equation.
             * It is represented by a signed 16-bit value.
             */
            int tOutHigh = tempI2C.readRegister(0x2B);
            int tOutLow = tempI2C.readRegister(0x2A);

            String tOutHighString = Integer.toBinaryString(tOutHigh);
            String tOutLowString = Integer.toBinaryString(tOutLow);

            tOutHighString = fillEightBit(tOutHighString);
            tOutLowString = fillEightBit(tOutLowString);

            String tOut = tOutHighString + tOutLowString;

            int tOutInt = 0;

            if(tOut.charAt(0) == '1'){
                tOutInt = fromTwosComplement(tOut);
            }else{
                tOutInt = Integer.parseInt(tOut, 2);
            }

            /**
             * Now that we have the equation for our line, and the independent
             * variable represented by tOut, we can calculate our temperature
             * reading (degrees Celsius)
             */
            temp = (slope * tOutInt) + b;

            //System.out.println(temp);
        } catch (Exception e){
            System.out.println(e);
        }


        return temp;
    }

    public static double getHumidity(){
        double humidity = 0;
        I2C humI2C = getI2C("HUMIDITY", HTS221_ADDRESS);

        try  {

            //Initialization
            //Set power status to on, BDU to non-continuous mode
            humI2C.writeRegister(0x20, 0x84);
            //Send the "one-shot" signal
            humI2C.writeRegister(0x21, 0x01);

            //Wait for the one-shot register to set itself back to zero, indicating a reading is ready.
            while(humI2C.readRegister(0x21) != 0){

            }

            int h0Cal = humI2C.readRegister(0x30);
            int h1Cal = humI2C.readRegister(0x31);

            String h1CalString = Integer.toBinaryString(h1Cal);
            String h0CalString = Integer.toBinaryString(h0Cal);

            /**
             * Convert the values to doubles, and divide by 2 (datasheet indicates that the registers hold a value
             * representing 2x the actual calibration value)
             */
            double h0CalDouble = Integer.parseInt(h0CalString, 2) / 2;
            double h1CalDouble = Integer.parseInt(h1CalString, 2) / 2;

            /**
             * The values we just calculated represent the y-values of two points.
             *
             * To determine the equation of the line that describes the humidity
             * reading, we also need the x-values, represented by the H0 and H1 values
             */

            //H0 and H1 are stored in two separate registers each, as a signed 16 bit value.
            int h0High = humI2C.readRegister(0x37);
            int h0Low = humI2C.readRegister(0x36);
            int h1High = humI2C.readRegister(0x3B);
            int h1Low = humI2C.readRegister(0x3A);

            String h0HighString = Integer.toBinaryString(h0High);
            String h0LowString = Integer.toBinaryString(h0Low);
            String h1HighString = Integer.toBinaryString(h1High);
            String h1LowString = Integer.toBinaryString(h1Low);

            h0HighString = fillEightBit(h0HighString);
            h0LowString = fillEightBit(h0LowString);
            h1HighString = fillEightBit(h1HighString);
            h1LowString = fillEightBit(h1LowString);

            //Concatenate to obtain our 16 bit values for h0 and h1
            String h0String = h0HighString + h0LowString;
            String h1String = h1HighString + h1LowString;

            int h0 = 0;

            if(h0String.charAt(0) == '1'){
                h0 = fromTwosComplement(h0String);
            }else{
                h0 = Integer.parseInt(h0String, 2);
            }

            int h1 = 0;

            if(h1String.charAt(0) == '1'){
                h1 = fromTwosComplement(h1String);
            }else{
                h1 = Integer.parseInt(h1String, 2);
            }

            /**
             * Now that we have two points, we can calculate the slope
             * by dividing rise by run
             */
            Double slope = (h1CalDouble - h0CalDouble) / (h1 - h0);

            /**
             * And the y-intercept can be determined by isolating for it
             * in the formula (y = mx + b, b = y - mx)
             */
            Double b = h1CalDouble - (slope * h1);

            /**
             * The value in HOUT represents the independent variable for the above line equation.
             * It is represented by a signed 16-bit value.
             */
            int hOutHigh = humI2C.readRegister(0x29);
            int hOutLow = humI2C.readRegister(0x28);

            String hOutHighString = Integer.toBinaryString(hOutHigh);
            String hOutLowString = Integer.toBinaryString(hOutLow);

            hOutHighString = fillEightBit(hOutHighString);
            hOutLowString = fillEightBit(hOutLowString);

            String hOut = hOutHighString + hOutLowString;

            int hOutInt = 0;

            if(hOut.charAt(0) == '1'){
                hOutInt = fromTwosComplement(hOut);
            }else{
                hOutInt = Integer.parseInt(hOut, 2);
            }

            /**
             * Now that we have the equation for our line, and the independent
             * variable represented by tOut, we can calculate our relative humidity
             * (percent)
             */
            humidity = (slope * hOutInt) + b;

        } catch (Exception e){
            System.out.println(e);
        }


        return humidity;
    }

    public static I2C getI2C(String id, int _ADDRESS){
        I2C i2c;

        Context pi4j = Pi4J.newAutoContext();
        I2CProvider i2CProvider = pi4j.provider("linuxfs-i2c");
        I2CConfig i2cConfig = I2C.newConfigBuilder(pi4j).id(id).bus(1).device(_ADDRESS).build();
        i2c = i2CProvider.create(i2cConfig);

        return i2c;
    }

    /**
     * Converts a passed binary string in two's complement to
     * it's decimal equivalent
     */
    public static int fromTwosComplement(String binary){
        int converted = 0;

        String twos = "", ones = "";

        for (int i = 0; i < binary.length(); i++) {
            ones += binary.charAt(i) == '0' ? "1" : "0";
        }


        StringBuilder builder = new StringBuilder(ones);
        boolean b = false;
        for (int i = ones.length() - 1; i > 0; i--) {
            if (ones.charAt(i) == '1') {
                builder.setCharAt(i, '0');
            } else {
                builder.setCharAt(i, '1');
                b = true;
                break;
            }
        }
        if (!b)
            builder.append("1", 0, 7);

        twos = builder.toString();

        converted = Integer.parseInt(twos, 2);

        //System.out.println(twos);

        converted = converted * -1;

        return converted;
    }

    static public String fillEightBit(String eightBit){
        while(eightBit.length() < 8){
            eightBit = '0' + eightBit;
        }
        return eightBit;
    }
}