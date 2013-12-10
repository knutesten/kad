package no.mesan.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import no.mesan.model.Topic;
import no.mesan.persistence.topic.TopicDao;

/**
 * TODO
 *
 * @author Knut Esten Melandsø Nekså
 */

@Named
@RequestScoped
public class FrontPageController {
    private List<Topic> topicList;
    @Inject
    private TopicDao topicDao;

    @PostConstruct
    public void initialize() {

    }

    public List<Topic> getTopicList() {
        return topicList;
    }
}
