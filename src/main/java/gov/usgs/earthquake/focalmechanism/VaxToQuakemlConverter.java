package gov.usgs.earthquake.focalmechanism;
import gov.usgs.earthquake.quakeml.FileToQuakemlConverter;



import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.quakeml_1_2.Quakeml;

public class VaxToQuakemlConverter extends RawMechanismConverter implements FileToQuakemlConverter {
    
        
    public VaxToQuakemlConverter(){
        eventMagnitudes = new ArrayList<BigDecimal>(1);
    }
    public Quakeml parseFile(final File file) throws Exception{
        String fname = file.getName();
        String parts[] = fname.split("\\.");
        eventID = parts[0];
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            parseInputFormat(br);
        }
        catch(IOException e){
            throw(e);
        }
        return convertToQuakeml();
    }

    public void parseInputFormat(BufferedReader br) throws IOException{
        debug = true;
        try{
            //Read in the date and time from the first line
            String firstline = br.readLine();
            if (debug){
                System.out.println("Line 1");
            }
            //Note this will give wrong date for events before 2000
            eventTime = this.readTime(firstline);
            
            //second line is blank
            br.readLine();
            
            //third line has lat/lon
            String[] parts;
            String thirdline = br.readLine();
            if (debug){
                System.out.println("Line 3");
            }
            parts = thirdline.split(":");
            String epistr = parts[1].trim();
            parts = epistr.split("\\s+");
            eventLatitude = new BigDecimal((parts[0]));
            eventLongitude = new BigDecimal((parts[1]));
            
            //fourth line has the magnitude
            String fourthline = br.readLine();
            if (debug){
                System.out.println("Line 4");
            }
            parts = fourthline.split("\\s+");
            BigDecimal mag1 = new BigDecimal(parts[1].trim());
            eventMagnitudes.add(mag1);

            //fifth line is blank
            br.readLine();
            
            //sixth line has the source
            String sixthline = br.readLine();
            if (debug){
                System.out.println("Line 6");
            }
            eventSource = "us";
            mechanismSource = eventSource;
            String mtType = new String(sixthline.substring(sixthline.indexOf('/'))); // trim().replace("/", " "));
            if (mtType.contains("WPHASE"))
            {
            	this.momentTensorType="Mww";
            	parseWCMT(br);
            } else if (mtType.contains("REGIONAL"))
            {
            	this.momentTensorType="Mwr";
            	parseRMT(br);
            } else
            	this.momentTensorType = "Unknown";          
            
        }
        catch (IOException e){
            throw(e);
        } 
    }
    
    protected void parseRMT(BufferedReader br) throws IOException{
    	//7th line has the derived depth and number of stations for RMT
        String seventhline = br.readLine();
        if (debug){
            System.out.println("Line 7");
        }
        parseDepthNumStationsLine(seventhline);
        parseMomentTensor(br);
        parsePrincipalAxes(br);
        
        //next line is blank
        br.readLine();
        
        parseDoubleCouple(br);
        
        //need to add that evaluation Mode is Manual and Status is Reviewed by default
        // gavin will add in data in file to be able to say it is prelim or final status
        
        //RMT does not provide a derived position or time, but it does provide depth,
        //so we want to be able to put that into the QuakeML.  To do this, we need to
        //have derived lat/lon/time values set.  Because they are not inverted for, we
        //will set them to the input values.
        derivedEventLatitude = eventLatitude;
        derivedEventLongitude = eventLongitude;
        derivedEventTime = eventTime;
        
        //set the derived origin booleans
        derivedTimeFixed = true;
        derivedEpicenterFixed = true;
    }
    
    protected void parseWCMT(BufferedReader br) throws IOException{
        
    	//seventh line has the derived centroid time in WCMT
        String seventhline = br.readLine();
        if (debug){
            System.out.println("Line 7");
        }
        // in WCMT has line 7 is  time, in RMT line 7 is depth
        derivedEventTime = this.readTime(seventhline);
        
      //eighth line has the derived epicenter for WCMT
        String eighthline = br.readLine();
        if (debug){
            System.out.println("Line 8");
        }
        parseDerivedLatLon(eighthline);
        
       //ninth line has the derived depth and number of stations for WCMT
        String ninthline = br.readLine();
        if (debug){
            System.out.println("Line 9");
        }
        parseDepthNumStationsLine(ninthline);
        
        //lines 10 thru 13
        parseMomentTensor(br);
        
        //lines 14 - 17
        parsePrincipalAxes(br);
        
        //next line is blank (line 18)
        br.readLine();
        
        parseDoubleCouple(br);
        
        //set the derived origin booleans
        derivedTimeFixed = false;
        derivedEpicenterFixed = false;
    }

    private BigDecimal[] parseAxis(String input){
        String line = input;
        line = line.trim().substring(1); //strip off leading axis character
        line = line.replaceAll("[a-zA-Z]*=","").trim();
        String[] parts;
        parts = line.split("\\s+");
        BigDecimal[] values;
        values = new BigDecimal[3];
        values[0] = new BigDecimal(parts[0].trim());
        values[1] = new BigDecimal(parts[1].trim());
        values[2] = new BigDecimal(parts[2].trim());
        return(values);
    }

    private BigDecimal[] parsePlane(String line){
        String parts[];
        parts = line.split(":");
        String astr = parts[1].trim();
        astr = astr.replaceAll("[a-zA-Z]*=","");
        astr = astr.trim();
        parts = astr.split("\\s+");
        BigDecimal[] values;
        values = new BigDecimal[3];
        values[0] = new BigDecimal(parts[0].trim());
        values[1] = new BigDecimal(parts[1].trim());
        values[2] = new BigDecimal(parts[2].trim());
        return(values);
    }

    private double[] parseComponents(String input,double exponent){
        String line = input;
        line = line.replaceAll("[a-zA-Z]{3}=","").trim(); //get rid of Mrr=
        String parts[];
        parts = line.split("\\s+");
        double[] values;
        values = new double[2];
        //one would need to Add 7 to exponent to get to Dynes-cm units (from Newton-meters)
        //Data is in Newton meters, we want newton meters in quakeml
        values[0] = Double.parseDouble(parts[0].trim()) * Math.pow(10.0,exponent);
        values[1] = Double.parseDouble(parts[1].trim()) * Math.pow(10.0,exponent);
        return(values);
    }

    private Date readTime(String line){
        int year = Integer.parseInt(line.substring(0,2))+2000; 
        int month = Integer.parseInt(line.substring(3,5).trim())-1; //Calendar months start at 0
        int day = Integer.parseInt(line.substring(6,8).trim());
        int hour = Integer.parseInt(line.substring(9,11).trim());
        int minute = Integer.parseInt(line.substring(12,14).trim());
        int endLine = line.length();
        //some have decimal seconds some do not
        String secstr = line.substring(15,endLine);
        double dd = new Double(secstr).doubleValue()*1000.0;
        int milliseconds = new Double(dd).intValue();
        GregorianCalendar c = new GregorianCalendar(TimeZone.getTimeZone("GMT"),Locale.US);
        c.set(year,month,day,hour,minute,0);
        Date eventTime = c.getTime();
        eventTime.setTime(eventTime.getTime()+milliseconds);
        return(eventTime);
    }
    
    protected void parseDoubleCouple(BufferedReader br) throws IOException
    {
    	BigDecimal values[];
    	
    	//nineteenth line has the moment
        String nineteenthline = br.readLine().trim();
        if (debug){
            System.out.format("Line 19: '%s'\n",nineteenthline);
        }
        Matcher m = Pattern.compile("Mo=").matcher(nineteenthline);
        Matcher a = Pattern.compile("\\*").matcher(nineteenthline);
        m.find();
        a.find();
        String manstr = nineteenthline.substring(m.start()+3,a.start());
        double mantissa = Double.parseDouble(manstr);
        int linelen = nineteenthline.length();
        double expo = Double.parseDouble(nineteenthline.substring(linelen-2));
        //scalarMoment = new BigDecimal(mantissa*Math.pow(10.0,expo)*Math.pow(10.0,7.0));
        scalarMoment = new BigDecimal(mantissa*Math.pow(10.0,expo)); //N m
        
        //twentieth line has the NP1 values
        String twentiethline = br.readLine().trim();
        if (debug){
            System.out.format("Line 20:'%s'\n",twentiethline);
        }
        values = parsePlane(twentiethline);
        nodalPlane1Strike = values[0];
        nodalPlane1Dip = values[1];
        nodalPlane1Slip = values[2];

        //twentyfirst line has the NP1 values
        String twentyfirstline = br.readLine().trim();
        if (debug){
            System.out.format("Line 21:'%s'\n", twentyfirstline);
        }
        values = parsePlane(twentyfirstline);
        nodalPlane2Strike = values[0];
        nodalPlane2Dip = values[1];
        nodalPlane2Slip = values[2];
    
    }

    protected void parseDepthNumStationsLine (String line) throws IOException
    {
    	String[] parts;
    	int i0=0,i1=0;
    	try {
    	
    	Matcher mdepth = Pattern.compile("Depth\\s+[0-9]*").matcher(line);
       
        if (mdepth.find()){
            i0 = mdepth.start()+5;
            i1 = mdepth.end();
            derivedEventDepth = new BigDecimal((line.substring(i0,i1).trim()));
            //quakeml depths are in meters, not km
            derivedEventDepth = derivedEventDepth.multiply(new BigDecimal(1000));
            String stastr = line.substring(i1+1);
            parts = stastr.split(":");
            numStations = new BigInteger(parts[1].trim()); //number of long period body wave stations
        }
        else{
            //derivedDepth = 0;
        }
    	} catch (Exception e) {
    		throw new IOException(e.getMessage());
    	}
    }
    
    protected void parseDerivedLatLon( String line)
    {
    	String[] parts;
    	String epistr;
    	
    	parts = line.split(":");
        epistr = parts[1].trim();
        parts = epistr.split("\\s+");
        derivedEventLatitude = new BigDecimal((parts[0]));
        derivedEventLongitude = new BigDecimal((parts[1]));
    }
    
    protected void parseMomentTensor(BufferedReader br) throws IOException
    {
    	String[] parts;
    	String lineA = br.readLine();
        if (debug){
            System.out.format("Line mt 1: '%s'\n",lineA);
        }
        
    	//tenth line has moment tensor scale
        parts = lineA.split(";");
        int idx = parts[1].indexOf("Nm");
        String expstr = parts[1].substring(idx-3,idx-1);
        double exponent = Double.parseDouble(expstr);
        
      // line has Mrr and Mtt
        String lineB = br.readLine().trim();
        if (debug){
            System.out.println("Line 11");
        }
        double compvalues[];
        compvalues = parseComponents(lineB,exponent);
        tensorMrr = new BigDecimal(compvalues[0]);
        tensorMtt = new BigDecimal(compvalues[1]);

        // line has Mpp and Mrt
        String lineC = br.readLine();
        if (debug){
            System.out.println("Line c");
        }
        compvalues = parseComponents(lineC,exponent);
        tensorMpp = new BigDecimal(compvalues[0]);
        tensorMrt = new BigDecimal(compvalues[1]);
        
        //thirteenth line has Mrp and Mtp
        String lineD = br.readLine();
        if (debug){
            System.out.println("Line D");
        }
        compvalues = parseComponents(lineD,exponent);
        tensorMrp = new BigDecimal(compvalues[0]);
        tensorMtp = new BigDecimal(compvalues[1]);
    }
    
    protected void parsePrincipalAxes(BufferedReader br) throws IOException
    {
    	//fourteenth line has text we can skip
        br.readLine();
        
        //fifteenth line has the T axis values
        String fifteenthline = br.readLine().trim();
        if (debug){
            System.out.println("Line 15");
        }
        BigDecimal values[];
        values = parseAxis(fifteenthline);
        eigenVectorValues[0] = values[0];
        eigenVectorPlunges[0] = values[1];
        eigenVectorAzimuths[0] = values[2];

        //sixteenth line has the N axis values
        String sixteenthline = br.readLine().trim();
        if (debug){
            System.out.println("Line 16");
        }
        values = parseAxis(sixteenthline);
        eigenVectorValues[1] = values[0];
        eigenVectorPlunges[1] = values[1];
        eigenVectorAzimuths[1] = values[2];

        //seventeenth line has the P axis values
        String seventeenthline = br.readLine().trim();
        if (debug){
            System.out.println("Line 17");
        }
        values = parseAxis(seventeenthline);
        eigenVectorValues[2] = values[0];
        eigenVectorPlunges[2] = values[1];
        eigenVectorAzimuths[2] = values[2];
    }
}