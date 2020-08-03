package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class Breadcrumb {

    public String getText() {
        return text;
    }

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    protected String text;

}
