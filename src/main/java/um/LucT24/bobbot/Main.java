package um.LucT24.bobbot;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.shard.LoginEvent;
import sx.blah.discord.util.MessageBuilder;

import java.util.*;

/**
 * Created by Lucte on 1/28/2017.
 */
public class Main {
    static Random random = new Random();
    static List<String> responses;
    static IDiscordClient client;
    static ChatterBot bot;
    static ChatterBotSession session;
    public static void main(String[] args) throws Exception {
        bot = new ChatterBotFactory().create(ChatterBotType.CLEVERBOT);
        session = bot.createSession(Locale.UK);

        responses = new ArrayList<>();
        Collections.addAll(responses, "a", "b", "c", "d");
        ClientBuilder builder = new ClientBuilder().withToken(args[0]);
        builder.registerListener(new Listener());
        client = builder.login();

    }
    private static int rand(int max){
        return Math.abs(random.nextInt() % (max + 1));
    }
    public static class Listener{
        @EventSubscriber
        public void handle(MessageReceivedEvent event) throws Exception {
            if (event.getMessage().getContent().startsWith("!echo")){
                new MessageBuilder(client).withContent(event.getMessage().getContent().replace("!echo", "")).withChannel(event.getChannel()).send();
            }else if (event.getMessage().getContent().startsWith("!rep")){
                new MessageBuilder(client).withContent(responses.get(rand(responses.size()-1))).withChannel(event.getChannel()).send();
            }else if (event.getMessage().getContent().startsWith("!chat")){
                new MessageBuilder(client).withChannel(event.getChannel()).withContent(session.think(event.getMessage().getContent().substring("!chat ".length()))).send();
            }
        }
    }
}
