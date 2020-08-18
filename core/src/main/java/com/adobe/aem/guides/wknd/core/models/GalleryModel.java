package com.adobe.aem.guides.wknd.core.models;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

@Model(adaptables = Resource.class)
public class GalleryModel {

    @ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
    @Default(values = "No resourceType")
    protected String resourceType;

    String LANDING_TEMPLATE = "/conf/wknd/settings/wcm/templates/landing-page-template";

    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;

    private List<Article> articleList = new ArrayList<>();
    private String path;
    private String templateResult;
    private Iterator<Page> childPath;
    private String articlePath;
    private String articleTitle;
    private Date articleDate;
    //private String imgArticle;
    private String landingTitle;
    private Date landingDate;

    public boolean isTemplate() {
        if (templateResult.equals(LANDING_TEMPLATE)) {
            return true;
        }
        return false;
    }

    @PostConstruct
    protected void init() {

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(currentResource);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");

        path = currentPagePath;
        templateResult = currentPage.getTemplate().getPath();
        childPath = currentPage.listChildren();

        if (isTemplate()) {

            ValueMap valueMap = currentPage.getProperties();
            landingTitle = valueMap.get(JcrConstants.JCR_TITLE, String.class);
            landingDate = valueMap.get(JcrConstants.JCR_CREATED, Date.class);

            for (Iterator<Page> it = childPath; it.hasNext(); ) {
                Page page = it.next();
                articlePath = page.getPath();
                ValueMap valuemap = page.getProperties();
                articleTitle = valuemap.get(JcrConstants.JCR_TITLE, String.class);
                articleDate = valuemap.get(JcrConstants.JCR_CREATED, Date.class);
                //imgArticle = (jrc:content/root/responsivegrid/image)
                Article article = new Article(articlePath, articleTitle, articleDate);
                articleList.add(article);
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getLandingTitle() {
        return landingTitle;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

}
