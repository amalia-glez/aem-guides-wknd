package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class)
public class Breadcrumb {

    @ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "No resourceType")
    protected String resourceType;

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    private List<Page> pageList = new ArrayList<>();

    private String path;

    private String currentPageResult;

    private String pathPage;

    private int length;

    @PostConstruct
    protected void init() {

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(currentResource);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");
        Page currentPageResult = currentPage;
        path = currentPagePath;
        pathPage = currentPage.getContentResource().getResourceType();
        length = currentPage.getDepth();

        for (int i = 0; i < length; i++) {
            pageList.add(currentPage.getAbsoluteParent(i));
        }
    }

    public String getPath() {
        return path;
    }

    public String getPathPage() {
        return pathPage;
    }

    public int getLength() {
        return length;
    }

    public List<Page> getPageList() {
        return pageList;
    }

}
