package com.kidzpoems.ireland.dublin.navsingh;
/***********
 * A navsingh Studios Production
 * 
 * @author Navjot Singh Virk
 * Website: Navsingh.org.uk
 * Company: NAVSINGH Studio
 * Date: 12/08/2015
 * Time: 1:23 am
 *
 ***********/
public class ListModel {
	private  String headingName="";
	private String linkName="";
    private  String image="";
    private String imagelinkName="";

    /*********** Set Methods ******************/
    
    public void setHeadingName(String headingName)
    {
        this.headingName = headingName;
    }
    
    public void setLinkName (String linkName)
    {
    	this.linkName = linkName;
    }
    
    public void setImageLinkName (String imagelinkName)
    {
    	this.imagelinkName = imagelinkName;
    }
     
    public void setImage(String image)
    {
        this.image = image;
    }
     
     
    /*********** Get Methods ****************/
     
    public String getHeadingName()
    {
        return this.headingName;
    }
    
    public String getLinkName()
    {
    	return this.linkName;
    }
    
    public String getImageLinkName()
    {
    	return this.imagelinkName;
    }
     
    public String getImage()
    {
        return this.image;
    }
 
       
}