package gov.usgs.earthquake.focalmechanism;

import java.util.Date;

import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.math.BigInteger;

import gov.usgs.earthquake.quakeml.*;

import org.quakeml_1_2rc3.CreationInfo;
import org.quakeml_1_2rc3.Event;
import org.quakeml_1_2rc3.EventParameters;
import org.quakeml_1_2rc3.EventType;
import org.quakeml_1_2rc3.FocalMechanism;
import org.quakeml_1_2rc3.MomentTensor;
import org.quakeml_1_2rc3.NodalPlane;
import org.quakeml_1_2rc3.NodalPlanes;
import org.quakeml_1_2rc3.Origin;
import org.quakeml_1_2rc3.OriginQuality;
import org.quakeml_1_2rc3.Magnitude;
import org.quakeml_1_2rc3.Tensor;
import org.quakeml_1_2rc3.Quakeml;
import org.quakeml_1_2rc3.DataUsed;
import org.quakeml_1_2rc3.DataUsedWaveType;
import org.quakeml_1_2rc3.PrincipalAxes;
import org.quakeml_1_2rc3.Axis;
import org.quakeml_1_2rc3.RealQuantity;
import org.quakeml_1_2rc3.TimeQuantity;

public abstract class RawMechanismConverter{
	Boolean debug = false;
    Date eventTime;
    BigDecimal eventLatitude = null;
    BigDecimal eventLongitude = null;
    ArrayList<BigDecimal> eventMagnitudes = null;
    String eventLocation = null;
    BigDecimal eventDepth = null;
    String eventSource = null;
    String mechanismSource = null;
    String eventID = null;
    String momentTensorType = null;
    BigInteger numStations = null;
    BigDecimal shortestPeriodUsed = null;
    BigInteger numComponents = null;
    
    BigInteger BodyWaveStations = null;
    BigInteger BodyWaveComponents = null;
    BigDecimal BodyWaveShortestPeriod = null;

    BigInteger SurfaceWaveStations = null;
    BigInteger SurfaceWaveComponents = null;
    BigDecimal SurfaceWaveShortestPeriod = null;

    BigInteger MantleWaveStations = null;
    BigInteger MantleWaveComponents = null;
    BigDecimal MantleWaveShortestPeriod = null;

    String sourceInversionType = null;

    String momentRateFunction = null;
    BigDecimal momentRateFunctionDuration = null;
    Date derivedEventTime;
    BigDecimal derivedEventLatitude = null;
    BigDecimal derivedEventLongitude = null;
    BigDecimal derivedEventDepth = null;
    BigDecimal derivedEventTimeError = null; //seconds
    BigDecimal derivedEventLatitudeError = null;
    BigDecimal derivedEventLongitudeError = null;
    BigDecimal derivedEventDepthError = null;
    String derivedDepthType = null;
    //Calendar analysisTime = null;
    
    BigDecimal tensorMrr = null;
    BigDecimal tensorMtt = null;
    BigDecimal tensorMpp = null;
    BigDecimal tensorMrt = null;
    BigDecimal tensorMrp = null;
    BigDecimal tensorMtp = null;
    
    BigDecimal tensorMrrError = null;
    BigDecimal tensorMttError = null;
    BigDecimal tensorMppError = null;
    BigDecimal tensorMrtError = null;
    BigDecimal tensorMrpError = null;
    BigDecimal tensorMtpError = null;

    BigDecimal eigenVectorValues[] = {null,null,null};
    BigDecimal eigenVectorPlunges[] = {null,null,null};
    BigDecimal eigenVectorAzimuths[] = {null,null,null};
    
    BigDecimal scalarMoment = null; //Newton-meters

    BigDecimal nodalPlane1Strike = null;
    BigDecimal nodalPlane1Dip = null;
    BigDecimal nodalPlane1Slip = null;
    
    BigDecimal nodalPlane2Strike = null;
    BigDecimal nodalPlane2Dip = null;
    BigDecimal nodalPlane2Slip = null;

    BigDecimal percentDoubleCouple = null;
    
    String programVersion = null;

        
    public static double calculateMomentMagnitude(double scalarMoment){
    	//N m constant is 9.1
    	double mw = (2.0/3.0)*(Math.log10(scalarMoment) - 9.1);
        return mw;
    }
    BigDecimal getMomentMagnitude(){
    	return (new BigDecimal(calculateMomentMagnitude(scalarMoment.doubleValue())));        
    }
    Date getEventTime(){return eventTime;}
    BigDecimal getEventLatitude(){return eventLatitude;}
    BigDecimal getEventLongitude(){return eventLongitude;}
    BigDecimal getEventDepth(){return eventDepth;}
    ArrayList<BigDecimal> getEventMagnitudes(){return eventMagnitudes;}
    String getEventSource(){return eventSource;}
    String getMechanismSource(){return mechanismSource;}
    String getEventID(){return eventID;}
    String getMomentTensorType(){return momentTensorType;}
    BigInteger getNumStations(){return numStations;}
    BigInteger getNumComponents(){return numComponents;}
    BigDecimal getShortestPeriodUsed(){return shortestPeriodUsed;}
    
    BigInteger getBodyWaveStations(){return BodyWaveStations;}
    BigInteger getBodyWaveComponents(){return BodyWaveComponents;}
    BigDecimal getBodyWaveShortestPeriod(){return BodyWaveShortestPeriod;}

    BigInteger getSurfaceWaveStations(){return SurfaceWaveStations;}
    BigInteger getSurfaceWaveComponents(){return SurfaceWaveComponents;}
    BigDecimal getSurfaceWaveShortestPeriod(){return SurfaceWaveShortestPeriod;}

    BigInteger getMantleWaveStations(){return MantleWaveStations;}
    BigInteger getMantleWaveComponents(){return MantleWaveComponents;}
    BigDecimal getMantleWaveShortestPeriod(){return MantleWaveShortestPeriod;}
    
    String getMomentRateFunction(){return momentRateFunction;}
    BigDecimal getMomentRateFunctionDuration(){return momentRateFunctionDuration;}
    Date getDerivedEventTime(){return derivedEventTime;}
    BigDecimal getDerivedEventLatitude(){return derivedEventLatitude;}
    BigDecimal getDerivedEventLongitude(){return derivedEventLongitude;}
    BigDecimal getDerivedEventDepth(){return derivedEventDepth;}

    BigDecimal getDerivedEventTimeError(){return derivedEventTimeError;}
    BigDecimal getDerivedEventLatitudeError(){return derivedEventLatitudeError;}
    BigDecimal getDerivedEventLongitudeError(){return derivedEventLongitudeError;}
    BigDecimal getDerivedEventDepthError(){return derivedEventDepthError;}

    String getDerivedDepthType(){return derivedDepthType;}
    
    BigDecimal getTensorMrr(){return tensorMrr;}
    BigDecimal getTensorMtt(){return tensorMtt;}
    BigDecimal getTensorMpp(){return tensorMpp;}
    BigDecimal getTensorMrt(){return tensorMrt;}
    BigDecimal getTensorMrp(){return tensorMrp;}
    BigDecimal getTensorMtp(){return tensorMtp;}
    
    BigDecimal getTensorMrrError(){return tensorMrrError;}
    BigDecimal getTensorMttError(){return tensorMttError;}
    BigDecimal getTensorMppError(){return tensorMppError;}
    BigDecimal getTensorMrtError(){return tensorMrtError;}
    BigDecimal getTensorMrpError(){return tensorMrpError;}
    BigDecimal getTensorMtpError(){return tensorMtpError;}

    BigDecimal[] getEigenVectorValues(){return eigenVectorValues;}
    BigDecimal[] getEigenVectorPlunges(){return eigenVectorPlunges;}
    BigDecimal[] getEigenVectorAzimuths(){return eigenVectorAzimuths;}
    
    BigDecimal getScalarMoment(){return scalarMoment;}

    BigDecimal getNodalPlane1Strike(){return nodalPlane1Strike;}
    BigDecimal getNodalPlane1Dip(){return nodalPlane1Dip;}
    BigDecimal getNodalPlane1Slip(){return nodalPlane1Slip;}

    BigDecimal getNodalPlane2Strike(){return nodalPlane2Strike;}
    BigDecimal getNodalPlane2Dip(){return nodalPlane2Dip;}
    BigDecimal getNodalPlane2Slip(){return nodalPlane2Slip;}

    BigDecimal getPercentDoubleCouple(){return percentDoubleCouple;}
    String getProgramVersion(){return programVersion;}
    
    void parseInputFormat(BufferedReader br) throws IOException{
        //this must be implemented by subclass
    }
    /**
	 * Get a QuakeML publicID attribute.
	 * this should become a utility method, copied from CubeToQuakeml
	 * 
	 * @param dataSource
	 * @param eventId
	 * @param type
	 * @param other
	 * @return
	 * @throws Exception
	 */
	private String getQuakemlId(final String dataSource, final String eventId,
			final String type, final String other) throws Exception 
	{	
		return getQuakemlId(dataSource, eventId, type, other, true);
	}
	
	private String getQuakemlId(final String dataSource, final String eventId,
			final String type, final String other, boolean anssFlag) throws Exception 
	{
		String id;
		if(!anssFlag)
		{
			id = "quakeml:" + dataSource.toLowerCase() + "/" + 
			    type + "/" + eventId.toLowerCase() + "/" + other;
		} else {
			//QuakemlPublicId appends earthquake.usgs.gov to authority
		    id =  new QuakemlPublicId(dataSource.toLowerCase(), type,
				eventId.toLowerCase(), other).getPublicId();
		}
		return id;
	}
	

    public Quakeml convertToQuakeml()
    {
    	Quakeml quakeml = new Quakeml();
    	
    	//the mechanism source may or may not be different from event source
    	String localMechSrc;
    	if (getMechanismSource() == null)
    	{
    		localMechSrc = getEventSource();
    	} else {
    		localMechSrc = getMechanismSource();
    	}
    	
    	try {
               
    		Date created = new Date();

    		// event parameters id is unique to this message
    		EventParameters eventParameters = new EventParameters();
    		String source = getEventSource();
    		String id = getEventID();
    		String ctime = Long.toString(created.getTime());
    		String quakeid = getQuakemlId(source,id,"eventParameters",ctime);
    		eventParameters.setPublicID(quakeid); 
    		quakeml.setEventParameters(eventParameters);

    		Event event = new Event();
    		event.setPublicID(getQuakemlId(getEventSource(), getEventID(),
    				"event", null));
    		event.setType(EventType.EARTHQUAKE);
    		event.setDatasource(getEventSource().toLowerCase());
    		event.setEventid(getEventID().toLowerCase());
    		eventParameters.getEvents().add(event);

    		// who and when was this message sent
    		CreationInfo creationInfo = new CreationInfo();
    		creationInfo.setAgencyID(getMechanismSource()); //or other src??
    		creationInfo.setVersion(getProgramVersion()); //or other version??
    		creationInfo.setCreationTime(created);
    		event.setCreationInfo(creationInfo);
    		
      
            //Input Origin
            Origin origin = new Origin();
            event.getOrigins().add(origin);
    		origin.setPublicID(getQuakemlId(getEventSource(), getEventID(),
    				"origin", null));
    		
    		//input origin/event 
    		if(eventTime != null && eventLatitude != null && eventLongitude != null)
    		{
    		//origin time
    		TimeQuantity time = new TimeQuantity();
    		time.setValue(getEventTime());
    		origin.setTime(time);
    		
    		// origin latitude
    		RealQuantity latitude = new RealQuantity();
    		latitude.setValue(getEventLatitude());
    		origin.setLatitude(latitude);

    		// origin longitude
    		RealQuantity longitude = new RealQuantity();
    		longitude.setValue(getEventLongitude());
    		origin.setLongitude(longitude);

    		// origin depth
    		if(eventDepth!= null)
    		{
    		RealQuantity depth = new RealQuantity();
    		depth.setValue(getEventDepth()); //km
    		origin.setDepth(depth);
    		} //end origin depth null check
    		} //end input origin/event null check
           
    		
    		//Focal Mechanism
            FocalMechanism fm = new FocalMechanism();
            fm.setTriggeringOriginID(origin.getPublicID());
            fm.setPublicID(getQuakemlId(localMechSrc, getEventID(),
    				"focalMechanism", getMomentTensorType(), true));
            event.getFocalMechanisms().add(fm);
            convertFocalMechanism(fm);
                     
            //Moment Tensor
            MomentTensor mt = new MomentTensor();
            fm.setMomentTensor(mt);
            convertMomentTensor(mt);
                        
            //Moment Magnitude
            Magnitude mag = new Magnitude();
            mag.setPublicID(getQuakemlId(localMechSrc, getEventID(),
    				"magnitude", getMomentTensorType(), true));
            mt.setMomentMagnitudeID(mag.getPublicID());
            event.getMagnitudes().add(mag);
            
            RealQuantity magValue = new RealQuantity();
            magValue.setValue(getMomentMagnitude());
            mag.setMag(magValue);
            mag.setStationCount(getNumStations());
            mag.setType(getMomentTensorType());
            mag.setStationCount(getNumStations());
            mag.setOriginID(origin.getPublicID());
            
            //**************************************
            //Derived Origin
            if(derivedEventTime != null && derivedEventLatitude != null && derivedEventLongitude != null)
            {;
            Origin originDerived = new Origin();
            
            originDerived.setPublicID(getQuakemlId(localMechSrc, getEventID(),
    				"origin", getMomentTensorType(), true));
            
            mt.setDerivedOriginID(originDerived.getPublicID());
    		
            //origin time
    		TimeQuantity timeDerived = new TimeQuantity();
    		timeDerived.setValue(getDerivedEventTime());
    		originDerived.setTime(timeDerived);
    		
    		// origin latitude
    		RealQuantity latitudeDerived = new RealQuantity();
    		latitudeDerived.setValue(getDerivedEventLatitude());
    		originDerived.setLatitude(latitudeDerived);

    		// origin longitude
    		RealQuantity longitudeDerived = new RealQuantity();
    		longitudeDerived.setValue(getDerivedEventLongitude());
    		originDerived.setLongitude(longitudeDerived);

    		// origin depth
    		RealQuantity depthDerived = new RealQuantity();
    		depthDerived.setValue(getDerivedEventDepth()); //km
    		originDerived.setDepth(depthDerived);
            
    		OriginQuality originQualityDerived = new OriginQuality();
    		originDerived.setQuality(originQualityDerived);
    		
    		mt.setPublicID(getQuakemlId(localMechSrc, getEventID(), "momenttensor", getMomentTensorType(), true));
    		event.getOrigins().add(originDerived);
            }
    		
            
        } catch (Exception e) {
        		System.out.format("ERROR %s\n", e.getMessage());
        }
        return quakeml;
    }
    
    void convertFocalMechanism(FocalMechanism fm)
    {
    	//Focal Mechanism
                
        //Nodal Planes
        //Nodal Plane 1
        NodalPlanes nps = new NodalPlanes();
        if (nodalPlane1Dip != null && nodalPlane1Slip != null && nodalPlane1Strike != null)
        {
        
        fm.setNodalPlanes(nps);
        
        NodalPlane np1 = new NodalPlane();
        RealQuantity dip = new RealQuantity();
        dip.setValue(getNodalPlane1Dip());
        np1.setDip(dip);
        
        RealQuantity slip = new RealQuantity();
        slip.setValue(getNodalPlane1Slip());
        //rake = the direction of the slip
        np1.setRake(slip); 
        
        RealQuantity strike = new RealQuantity();
        strike.setValue(getNodalPlane1Strike());
        np1.setStrike(strike);
      
        nps.setNodalPlane1(np1);  
        }
        
        //Nodal Plane 2 
        if (nodalPlane2Dip != null && nodalPlane2Slip != null && nodalPlane2Strike != null)
        {
        NodalPlane np2 = new NodalPlane();
        RealQuantity dip2 = new RealQuantity();
        dip2.setValue(getNodalPlane2Dip());
        np2.setDip(dip2);
        
        RealQuantity slip2 = new RealQuantity();
        slip2.setValue(getNodalPlane2Slip());
        np2.setRake(slip2); 
        
        RealQuantity strike2 = new RealQuantity();
        strike2.setValue(getNodalPlane2Strike());
        np2.setStrike(strike2);
        nps.setNodalPlane2(np2);
        }
        
        //Principal Axis
        if (eigenVectorAzimuths.length>0 && eigenVectorPlunges.length > 0 && eigenVectorValues.length > 0)
        {
     
        	PrincipalAxes primaryAxis = new PrincipalAxes();
        	fm.setPrincipalAxes(primaryAxis);
        	
        	//T
        	Axis tAxis = new Axis();
        	RealQuantity azT = new RealQuantity();
        	azT.setValue(getEigenVectorAzimuths()[0]);
        	tAxis.setAzimuth(azT);
        	
        	RealQuantity lenT = new RealQuantity();
        	lenT.setValue(getEigenVectorValues()[0]);
        	tAxis.setLength(lenT);
        	
        	RealQuantity pluT = new RealQuantity();
        	pluT.setValue(getEigenVectorPlunges()[0]);
        	tAxis.setPlunge(pluT);
        	primaryAxis.setTAxis(tAxis);
        	
        	//N
        	Axis nAxis = new Axis();
        	RealQuantity azN = new RealQuantity();
        	azN.setValue(getEigenVectorAzimuths()[1]);
        	nAxis.setAzimuth(azN);
        	
        	RealQuantity lenN = new RealQuantity();
        	lenN.setValue(getEigenVectorValues()[1]);
        	nAxis.setLength(lenN);
        	
        	RealQuantity pluN = new RealQuantity();
        	pluN.setValue(getEigenVectorPlunges()[1]);
        	nAxis.setPlunge(pluN);
        	primaryAxis.setNAxis(nAxis);
        	
        	//P
        	Axis pAxis = new Axis();
        	RealQuantity azP = new RealQuantity();
        	azP.setValue(getEigenVectorAzimuths()[2]);
        	pAxis.setAzimuth(azP);
        	
        	RealQuantity lenP = new RealQuantity();
        	lenP.setValue(getEigenVectorValues()[2]);
        	pAxis.setLength(lenP);
        	
        	RealQuantity pluP = new RealQuantity();
        	pluP.setValue(getEigenVectorPlunges()[2]);
        	pAxis.setPlunge(pluP);
        	primaryAxis.setPAxis(pAxis);
        	
        }
    }
    
    void convertMomentTensor(MomentTensor mt)
    {
    	if(scalarMoment != null)
        {
            RealQuantity moment = new RealQuantity();
            moment.setValue(getScalarMoment());
            mt.setScalarMoment(moment);
        }
        
    	//this is used by PDL for product type for moment tensors
    	mt.setMethodID(getMomentTensorType());
    	
        Tensor tensor = new Tensor();
        mt.setTensor(tensor);
        
        if(tensorMrr != null)
        {
        RealQuantity mrr = new RealQuantity();
        mrr.setValue(getTensorMrr());
        //i am uncertain if is this really uncertainty?
        mrr.setUncertainty(getTensorMrrError()); 
        tensor.setMrr(mrr);
        }
        
        if(tensorMrp != null)
        {
        RealQuantity mrp = new RealQuantity();
        mrp.setValue(getTensorMrp());
        mrp.setUncertainty(getTensorMrpError());
        tensor.setMrp(mrp);
        }
        
        if(tensorMrt != null)
        {
        RealQuantity mrt = new RealQuantity();
        mrt.setValue(getTensorMrt());
        mrt.setUncertainty(getTensorMrtError());
        tensor.setMrt(mrt);
        }
        
        if(tensorMpp != null)
        {
        RealQuantity mpp = new RealQuantity();
        mpp.setValue(getTensorMpp());
        mpp.setUncertainty(getTensorMppError());
        tensor.setMpp(mpp);
        }
        
        if (tensorMtp != null)
        {
        RealQuantity mtp = new RealQuantity();
        mtp.setValue(getTensorMtp());
        mtp.setUncertainty(getTensorMtpError());
        tensor.setMtp(mtp);
        }
        
        if(tensorMtt != null)
        {
        RealQuantity mtt = new RealQuantity();
        mtt.setValue(getTensorMtt());
        mtt.setUncertainty(getTensorMttError());
        tensor.setMtt(mtt);
        }
        
        if(percentDoubleCouple != null)
        {
            mt.setDoubleCouple(getPercentDoubleCouple());
        }
        
        //DataUsed
        if(BodyWaveComponents != null)
        {
        DataUsed bwData = new DataUsed();
        bwData.setStationCount(getBodyWaveStations());
        bwData.setComponentCount(getBodyWaveComponents());
        bwData.setShortestPeriod(getBodyWaveShortestPeriod());
        bwData.setWaveType(DataUsedWaveType.BODY_WAVES);
        mt.getDataUseds().add(bwData);
        }
        
        if (SurfaceWaveComponents != null)
        {
        DataUsed swData = new DataUsed();
        swData.setStationCount(getSurfaceWaveStations());
        swData.setComponentCount(getSurfaceWaveComponents());
        swData.setShortestPeriod(getSurfaceWaveShortestPeriod());
        swData.setWaveType(DataUsedWaveType.SURFACE_WAVES);
        mt.getDataUseds().add(swData);
        }
        
        if (MantleWaveStations != null)
        {
        DataUsed mwData = new DataUsed();
        mwData.setStationCount(getMantleWaveStations());
        mwData.setComponentCount(getMantleWaveComponents());
        mwData.setShortestPeriod(getMantleWaveShortestPeriod());
        mwData.setWaveType(DataUsedWaveType.MANTLE_WAVES);
        mt.getDataUseds().add(mwData);
        }
    }
}