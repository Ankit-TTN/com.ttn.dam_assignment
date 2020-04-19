package com.ttn.core.models;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.dam.api.Rendition;
import com.day.cq.wcm.api.PageManager;
import com.day.text.Text;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageRendition extends WCMUsePojo {
    String path;
    @Override
    public void activate() throws Exception {
        Resource currentResource = getResource();
        ResourceResolver resourceResolver = getResourceResolver();
        ValueMap myValueMap = currentResource.getValueMap();
        Resource imageRes = resourceResolver.getResource((String) myValueMap.get("imageLink"));
        if(imageRes!=null){
            Asset imgAsset = imageRes.adaptTo(Asset.class);
            String[] dimension = ((String) myValueMap.get("resolution")).split(":");
            if(imgAsset!=null){
                path = imgAsset.getOriginal().getPath();
                String testString = (dimension[0]+"."+dimension[1]+".png");
                for (Rendition rendition : imgAsset.getRenditions()) {
                    if(rendition.getName().endsWith(testString)){
                        path = rendition.getPath();
                    }
                }
            }

        }

    }

    public String getPath() {
        return path;
    }


}
