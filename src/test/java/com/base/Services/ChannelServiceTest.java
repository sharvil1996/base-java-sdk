package com.base.Services;

import com.base.AbstractBaseTest;
import com.base.Base;
import com.base.Exceptions.BaseHttpException;
import com.base.Exceptions.ChannelNotFound;
import com.base.Exceptions.TeamNotFound;
import com.base.Http.Request.Request;
import com.base.Http.Response.Response;
import com.base.Http.Server.Responses.Channel.*;
import com.base.Models.Channel;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelServiceTest extends AbstractBaseTest {

    /**
     * Test case for Create Channel
     *
     * @throws BaseHttpException
     */
    @Test
    public void testChannelCreate() throws BaseHttpException {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", CreateChannelResponse.VALID_NAME);
        parameters.put("description", CreateChannelResponse.VALID_DESCRIPTION);
        parameters.put("color", CreateChannelResponse.VALID_COLOR);
        parameters.put("is_private", CreateChannelResponse.VALID_STATUS);

        Response response = this.base.sendRequest("/teams/".concat(CreateChannelResponse.VALID_TEAM_SLUG).concat("/channels"), Request.METHOD_POST, parameters);
        Channel channel = (Channel) Base.makeModel(Channel.class, response.getBody());
        Assert.assertEquals(channel.getName(), CreateChannelResponse.VALID_NAME);
        Assert.assertEquals(channel.getSlug(), CreateChannelResponse.VALID_CHANNEL_SLUG);
        Assert.assertEquals(channel.getDescription(), CreateChannelResponse.VALID_DESCRIPTION);
        Assert.assertEquals(channel.getColor(), CreateChannelResponse.VALID_COLOR);
        Assert.assertEquals(channel.getIs_private(), CreateChannelResponse.VALID_STATUS);
    }


    /**
     * @throws BaseHttpException
     * @throws TeamNotFound
     */
    @Test
    public void testGetAllChannels() throws BaseHttpException, TeamNotFound {

        List<Channel> channels = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            channels.add(new Channel().setColor(GetAllChannelsResponse.VALID_COLOR)
                    .setName(GetAllChannelsResponse.VALID_NAME.concat(" " + i))
                    .setDescription(GetAllChannelsResponse.VALID_DESCRIPTION)
                    .setIs_private(GetAllChannelsResponse.VALID_STATUS)
                    .setSlug(GetAllChannelsResponse.VALID_CHANNEL_SLUG.concat("-" + i)));
        }

        List<Channel> ActualChannel = base.channelService().getAllChannels(GetAllChannelsResponse.VALID_TEAM_SLUG);
        for (int i = 0; i < ActualChannel.size(); i++) {
            String actualName = ActualChannel.get(i).getName();
            String expectName = channels.get(i).getName();
            Assert.assertEquals(actualName, expectName);
        }
    }

    /**
     * @throws ChannelNotFound
     */
    @Test
    public void getDeleteChannel() throws ChannelNotFound {
        boolean result = base.channelService().deleteChannel(DeleteChannelResponse.VALID_TEAM_SLUG, DeleteChannelResponse.VALID_CHANNEL_SLUG);
        Assert.assertEquals(true, result);
    }

    /**
     * @throws BaseHttpException
     * @throws ChannelNotFound
     */
    @Test
    public void testUpdateChannel() throws BaseHttpException, ChannelNotFound {

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("name", UpdateChannelResponse.VALID_NAME);
        parameters.put("description", UpdateChannelResponse.VALID_DESCRIPTION);
        parameters.put("color", UpdateChannelResponse.VALID_COLOR);
        parameters.put("is_private", UpdateChannelResponse.VALID_STATUS);

        Response response = this.base.sendRequest("/teams/".concat(UpdateChannelResponse.VALID_TEAM_SLUG).concat("/channels/").concat(UpdateChannelResponse.VALID_CHANNEL_SLUG), Request.METHOD_PATCH, parameters);
        Channel channel = (Channel) Base.makeModel(Channel.class, response.getBody());
        Assert.assertEquals(channel.getName(), UpdateChannelResponse.VALID_NAME);
        Assert.assertEquals(channel.getSlug(), UpdateChannelResponse.VALID_CHANNEL_SLUG);
        Assert.assertEquals(channel.getDescription(), UpdateChannelResponse.VALID_DESCRIPTION);
        Assert.assertEquals(channel.getColor(), UpdateChannelResponse.VALID_COLOR);
        Assert.assertEquals(channel.getIs_private(), UpdateChannelResponse.VALID_STATUS);
    }

    /**
     * @throws BaseHttpException
     * @throws ChannelNotFound
     */
    @Test
    public void testGetChannel() throws BaseHttpException, ChannelNotFound {
        try {
            Channel channel = base.channelService().getChannel(GetChannelResponse.VALID_TEAM_SLUG, GetChannelResponse.VALID_CHANNEL_SLUG);
            Assert.assertEquals(channel.getName(), GetChannelResponse.VALID_NAME);
            Assert.assertEquals(channel.getDescription(), GetChannelResponse.VALID_DESCRIPTION);
            Assert.assertEquals(channel.getColor(), GetChannelResponse.VALID_COLOR);
            Assert.assertEquals(channel.getSlug(), GetChannelResponse.VALID_CHANNEL_SLUG);
            Assert.assertEquals(channel.getIs_private(), GetChannelResponse.VALID_STATUS);
        } catch (ChannelNotFound e) {
            Assert.fail(e.getMessage());
        }
    }
}
