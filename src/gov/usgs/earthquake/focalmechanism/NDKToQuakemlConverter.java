package gov.usgs.earthquake.focalmechanism;


import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.BigDecimal;
import java.math.BigInteger;

import gov.usgs.earthquake.quakeml.FileToQuakemlConverter;
import org.quakeml_1_2.Quakeml;


public class NDKToQuakemlConverter extends RawMechanismConverter implements FileToQuakemlConverter {

	boolean debug = true;
	
    public NDKToQuakemlConverter(){
    	eventMagnitudes = new ArrayList<BigDecimal>(2);
    }
    
    /**
	 * Read a File and translate to Quakeml.
	 * 
	 * @param file
	 *            the file to parse, may be a directory.
	 * @return Quakeml equivalent of file.
	 */
	public Quakeml parseFile(final File file) throws Exception
	{
    
       String firstline = "";
        //boolean debug = true; 
        
        //String mechanismSource = file.getName().substring(0, 4);
        //I think ONLY GCMT produces NDK so going with this for now
        //HARD CODED...do we need to determine this dynamically when parsing NDK format?
        mechanismSource = "ld"; //"GCMT.www.globalcmt.org";
        
        System.out.format("NDK2QuakeML parseFile \n");
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
                       
            br.mark(8192); //set a mark for where we are
            firstline = br.readLine();
            while (firstline != null){
                br.reset();
                parseInputFormat(br);
                if (debug){
                    //printSummary();
                    System.out.println("finished parse input.");
                }
                br.mark(8192);
                firstline = br.readLine();
            }
        }
        catch (Exception e){
            System.out.format("ERROR '%s' for message starting with '%s'\n",e.getMessage(),firstline);
            return null;
        }
        
        return convertToQuakeml();
    }
    
    double exponent = 0;
    //http://www.ldeo.columbia.edu/~gcmt/projects/CMT/catalog/allorder.ndk_explained
    void parseInputFormat(BufferedReader br) throws IOException{
        //boolean debug = true;
        
        if (debug) System.out.println("Starting Parsing NDK input.");  
        
        try{
            //parse first line
            String line = br.readLine();
            if (debug){
                System.out.format("Line 1: %s\n",line);
            }
            parseLine1(line);
            
            //parse second line
            line = br.readLine();
            if (debug){
                System.out.format("Line 2: %s\n",line);
            }
            parseLine2(line);
            
            //parse third line
            line = br.readLine();
            if (debug){
                System.out.format("Line 3: %s\n",line);
            }
            parseLine3(line);
            
            //parse fourth line
            line = br.readLine();
            if (debug){
                System.out.format("Line 4: %s\n",line);
            }
            parseLine4(line);

            //parse fifth line
            line = br.readLine();
            if (debug){
                System.out.format("Line 5: %s\n",line);
            }
            parseLine5(line);
            
        }
        /*catch (IOException ioe){*/
        catch (IOException ioe) {
        	System.out.format("ERROR parseing NDK '%s'\n", ioe.getMessage());
            throw ioe;
        }
        
               
    }
    
    void parseLine1(String line){
    	if (debug) System.out.format("Starting Parsing line 1 of NDK input\n");
        eventSource = line.substring(0,4).trim();
        //note that if eventSource != PDE then anssFlag needs to be FALSE
        /* First line: Hypocenter line
        [1-4]   Hypocenter reference catalog (e.g., PDE for USGS location, ISC for
        ISC catalog, SWE for surface-wave location, [Ekstrom, BSSA, 2006]) */
        
        //if source is PDE then translate it to US to indicate NEIC as source
        // but the PDE id does not match NEIC id on web so do we do this???
        //ToDo needs resolution!!!!!!!!!
        if(eventSource.compareTo("PDE")==0)
        {
        	eventSource = "US";
        } 
                
        String dstr = line.substring(5,26);
        int year = Integer.parseInt(dstr.substring(0,4));
        int month = Integer.parseInt(dstr.substring(5,7));
        int day = Integer.parseInt(dstr.substring(8,10));
        int hour = Integer.parseInt(dstr.substring(11,13));
        int minute = Integer.parseInt(dstr.substring(14,16));
        
                 
        String secstr = dstr.substring(17);
        double dd = new Double(secstr).doubleValue()*1000.0;

        int milliseconds = new Double(dd).intValue();
        GregorianCalendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT"),Locale.US);
        c.set(year,month,day,hour,minute,0);
        eventTime = c.getTime();
        eventTime.setTime(eventTime.getTime()+milliseconds);
               
        
        eventLatitude = new BigDecimal(line.substring(27,33).trim());
        eventLongitude = new BigDecimal(line.substring(34,41).trim());
        eventDepth = new BigDecimal(line.substring(42,47).trim());
        //depths in quakeml are in meters, not km
        eventDepth = eventDepth.multiply(new BigDecimal(1000));
        BigDecimal mag1 = new BigDecimal(line.substring(47,51).trim());
        BigDecimal mag2 = new BigDecimal(line.substring(51,55).trim());
        eventMagnitudes.add(mag1);
        eventMagnitudes.add(mag2);
        eventLocation = line.substring(56,80).trim();        
        
    }
    void parseLine2(String line){
        eventID = line.substring(0,16).trim();
        
        BodyWaveStations = new BigInteger(line.substring(19,22).trim());
        BodyWaveComponents = new BigInteger(line.substring(22,27).trim());
        BodyWaveShortestPeriod = new BigDecimal(line.substring(27,31).trim());

        SurfaceWaveStations = new BigInteger(line.substring(34,37).trim());
        SurfaceWaveComponents = new BigInteger(line.substring(37,42).trim());
        SurfaceWaveShortestPeriod = new BigDecimal(line.substring(42,46).trim());
        
        MantleWaveStations = new BigInteger(line.substring(49,52).trim());
        MantleWaveComponents = new BigInteger(line.substring(52,57).trim());
        MantleWaveShortestPeriod = new BigDecimal(line.substring(57,61).trim());

        numStations = new BigInteger(line.substring(49,52).trim()); //mantle wave stations
        numStations.add(getBodyWaveStations());
        numStations.add(getSurfaceWaveStations());
        
        String cmt = line.substring(62,68).trim();
        // is this always CMT when in ndk format?
        momentTensorType="Mwc"; //CMT is Mwc
        Matcher m0 = Pattern.compile("CMT:\\s*0").matcher(cmt);
        Matcher m1 = Pattern.compile("CMT:\\s*1").matcher(cmt);
        Matcher m2 = Pattern.compile("CMT:\\s*2").matcher(cmt);
        
        if (m0.find()){
            sourceInversionType = "general moment tensor"; //quakeml MomentTensorMethod.CMT_0
        }
        else if (m1.find()){
            sourceInversionType = "standard moment tensor"; //quakeml MomentTensorMethod.CMT_1
        }
        else if (m2.find()){
            sourceInversionType = "double couple source"; //quakeml MomentTensorMethod.CMT_2
        }
        else{
        	//maybe this should be NULL because we can't translate it to an enum
        	//sourceInversionType = null;
            sourceInversionType = "unknown source inversion"; //quakeml other options are Teleseismic, regional
        }
        momentRateFunction = line.substring(69,74);
        momentRateFunctionDuration = new BigDecimal(line.substring(75).trim()).multiply(new BigDecimal("2.0"));
    }

    void parseLine3(String line){
    	if (debug) System.out.format("Starting Parsing line 3 of NDK input\n");

        double dd = new Double(line.substring(9,18).trim()).doubleValue()*1000.0;
        int milliseconds = new Double(dd).intValue();
        derivedEventTime = new Date(eventTime.getTime());
        derivedEventTime.setTime(derivedEventTime.getTime()+milliseconds);

        derivedEventTimeError = new BigDecimal(line.substring(18,23).trim());
        derivedEventLatitude = new BigDecimal(line.substring(23,29).trim());
        derivedEventLatitudeError = new BigDecimal(line.substring(29,34).trim());
        derivedEventLongitude = new BigDecimal(line.substring(34,42).trim());
        derivedEventLongitudeError = new BigDecimal(line.substring(42,47).trim());
        derivedEventDepth = new BigDecimal(line.substring(47,53).trim()).multiply(new BigDecimal("1000"));
        derivedEventDepthError = new BigDecimal(line.substring(53,58).trim());
        derivedDepthType = new String(line.substring(58,61).trim());
    }
    
    void parseLine4(String line){
    	if (debug) System.out.format("Starting Parsing line 4 of NDK input\n");
        exponent = Double.parseDouble(line.substring(0,2));
        //we want units in N m
        if (exponent == 24)
        {
        	exponent = 17;
        }
        tensorMrr = new BigDecimal(Double.parseDouble(line.substring(2,9))*Math.pow(10.0,exponent));
        tensorMrrError = new BigDecimal(Double.parseDouble(line.substring(9,15))*Math.pow(10.0,exponent));
        tensorMtt = new BigDecimal(Double.parseDouble(line.substring(15,22))*Math.pow(10.0,exponent));
        tensorMttError = new BigDecimal(Double.parseDouble(line.substring(22,28))*Math.pow(10.0,exponent));
        tensorMpp = new BigDecimal(Double.parseDouble(line.substring(28,35))*Math.pow(10.0,exponent));
        tensorMppError = new BigDecimal(Double.parseDouble(line.substring(35,41))*Math.pow(10.0,exponent));
        tensorMrt = new BigDecimal(Double.parseDouble(line.substring(41,48))*Math.pow(10.0,exponent));
        tensorMrtError = new BigDecimal(Double.parseDouble(line.substring(48,54))*Math.pow(10.0,exponent));
        tensorMrp = new BigDecimal(Double.parseDouble(line.substring(54,61))*Math.pow(10.0,exponent));
        tensorMrpError = new BigDecimal(Double.parseDouble(line.substring(61,67))*Math.pow(10.0,exponent));
        tensorMtp = new BigDecimal(Double.parseDouble(line.substring(67,74))*Math.pow(10.0,exponent));
        tensorMtpError = new BigDecimal(Double.parseDouble(line.substring(74))*Math.pow(10.0,exponent));
    }
    void parseLine5(String line){
        programVersion = line.substring(0,3).trim();

        eigenVectorValues[0] = new BigDecimal(Double.parseDouble(line.substring(3,11))*Math.pow(10.0,exponent));
        eigenVectorPlunges[0] = new BigDecimal(line.substring(11,14).trim());
        eigenVectorAzimuths[0] = new BigDecimal(line.substring(14,18).trim());

        eigenVectorValues[1] = new BigDecimal(Double.parseDouble(line.substring(18,26))*Math.pow(10.0,exponent));
        eigenVectorPlunges[1] = new BigDecimal(line.substring(26,29).trim());
        eigenVectorAzimuths[1] = new BigDecimal(line.substring(29,33).trim());
        
        eigenVectorValues[2] = new BigDecimal(Double.parseDouble(line.substring(33,41))*Math.pow(10.0,exponent));
        eigenVectorPlunges[2] = new BigDecimal(line.substring(41,44).trim());
        eigenVectorAzimuths[2] = new BigDecimal(line.substring(44,48).trim());

        scalarMoment = new BigDecimal(Double.parseDouble(line.substring(49,56).trim())*Math.pow(10.0,exponent));

        nodalPlane1Strike = new BigDecimal(line.substring(56,60).trim());
        nodalPlane1Dip = new BigDecimal(line.substring(60,63).trim());
        nodalPlane1Slip = new BigDecimal(line.substring(63,68).trim());

        nodalPlane2Strike = new BigDecimal(line.substring(68,72).trim());
        nodalPlane2Dip = new BigDecimal(line.substring(72,75).trim());
        nodalPlane2Slip = new BigDecimal(line.substring(75).trim());
    }
}

