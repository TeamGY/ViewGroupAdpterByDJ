package com.hyj.containers;

import android.content.Context;

public class ResourceUtil { 
	
    public static int getLayoutId(Context paramContext, String paramString) { 
    	  
    return ThisResource.getIdByName(paramContext, "layout", paramString);
//        return paramContext.getResources().getIdentifier(paramString, "layout", 
//                paramContext.getPackageName()); 
    } 

    
    public static int getStringId(Context paramContext, String paramString) { 
    	
    	return ThisResource.getIdByName(paramContext, "string", paramString);
//        return paramContext.getResources().getIdentifier(paramString, "string", 
//                paramContext.getPackageName()); 
    } 
 
    public static int getDrawableId(Context paramContext, String paramString) { 
    	return ThisResource.getIdByName(paramContext, "drawable", paramString);
    	
//        return paramContext.getResources().getIdentifier(paramString, 
//                "drawable", paramContext.getPackageName()); 
    } 
     
    public static int getStyleId(Context paramContext, String paramString) { 
    	
    	return ThisResource.getIdByName(paramContext, "style", paramString);
    	
//        return paramContext.getResources().getIdentifier(paramString, 
//                "style", paramContext.getPackageName()); 
    } 
     
    public static int getId(Context paramContext, String paramString) { 
    	
    	return ThisResource.getIdByName(paramContext, "id", paramString);
    	
      //  return paramContext.getResources().getIdentifier(paramString,"id", paramContext.getPackageName()); 
    } 
     
    public static int getColorId(Context paramContext, String paramString) { 
    	
    	return ThisResource.getIdByName(paramContext, "color", paramString);
    	
//        return paramContext.getResources().getIdentifier(paramString, 
//                "color", paramContext.getPackageName()); 
    } 
    public static int getArrayId(Context paramContext, String paramString) { 
    	
    	return ThisResource.getIdByName(paramContext, "array", paramString);
    	
//        return paramContext.getResources().getIdentifier(paramString, 
//                "array", paramContext.getPackageName()); 
    } 
}