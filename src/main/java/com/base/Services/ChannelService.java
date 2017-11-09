package com.base.Services;

import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.TeamNotFound;
import com.base.Http.Request.Request;
import com.base.Http.Response.Response;
import com.base.Models.Channel;

import java.util.*;

public class ChannelService {

    private Base base;

    public ChannelService(Base base) {
        this.base = base;
    }

    /**
     * Create channel by slug name of team
     *
     * @param slug
     * @param name
     * @param description
     * @param color
     * @param is_private
     * @return Created Channel
     * @throws BaseHttpException
     */

    public Channel createChannel(String slug, String name, String description, String color, String is_private) throws BaseHttpException {
        Map<String, String> parameters = new HashMap<>();

        parameters.put("name", name);
        parameters.put("description", description);
        parameters.put("color", color);
        parameters.put("is_private", is_private);

        Response response = this.base.sendRequest("/teams/".concat(slug).concat("/channels"), Request.METHOD_POST, parameters);
        return (Channel) Base.makeModel(Channel.class, response.getBody());
    }

    /**
     * update channel with slug name
     *
     * @param name        Name of Channel
     * @param description Description of Channel
     * @param slug        Slug of Team
     * @return Updated Channel
     * @throws BaseHttpException
     */
    public Channel updateChannel(String slug, String name, String description, String color, String is_private) throws BaseHttpException {
        Map<String, String> parameters = new HashMap<>();
        if (!name.isEmpty()) {
            parameters.put("name", name);
        }
        if (!description.isEmpty()) {
            parameters.put("description", description);
        }
        if (!color.isEmpty()) {
            parameters.put("color", color);
        }
        if (!is_private.isEmpty()) {
            parameters.put("is_private", is_private);
        }

        parameters.put("name", name);
        parameters.put("description", description);
        parameters.put("color", color);
        parameters.put("is_private", is_private);

        Response response = this.base.sendRequest("/teams/".concat(slug).concat("/channels").concat("name"), Request.METHOD_PATCH, parameters);
        return (Channel) Base.makeModel(Channel.class, response.getBody());
    }

    /**
     * Get All Channels By Slug
     *
     * @param slug Slug of Team
     * @return List of Channel in slug
     * @throws ChannelNotFound
     */
    public List<Channel> allChannels(String slug) throws ChannelNotFound {
        try {
            Response response = base.sendRequest("/teams/".concat(slug).concat("/channels"), Request.METHOD_GET);
            Channel[] channelArray = (Channel[]) Base.makeModel(Channel[].class, response.getBody());
            return new ArrayList<>(Arrays.asList(channelArray));
        } catch (BaseHttpException e) {
            throw new ChannelNotFound(slug);
        }
    }

    /**
     * Delete Channel by Slug
     *
     * @param slug
     * @param name
     * @throws ChannelNotFound
     */
    public void deleteChannel(String slug, String name) throws ChannelNotFound {
        try {
            Response response = this.base.sendRequest("/teams/".concat(slug).concat("/channels").concat(name), Request.METHOD_DELETE);
        } catch (BaseHttpException e) {
            throw new ChannelNotFound(slug);
        }
    }


}
