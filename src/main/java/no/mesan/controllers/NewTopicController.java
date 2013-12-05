package no.mesan.controllers;

import no.mesan.persistence.topic.TopicDao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * TODO
 *
 * @author Dean Lozo
 */
@Named
@RequestScoped
public class NewTopicController {
    @Inject
    private TopicDao topicDao;

    

}
