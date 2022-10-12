package mm.client.proxy;

import mm.util.proxy.Proxy;
import net.minecraft.client.Minecraft;

/**
 * Implementation of the {@linkplain Proxy} interface.
 * Resolves client side values and resources.
 */
public class ClientProxy implements Proxy {

    @Override
    public String getUser() {
        var mcClient = Minecraft.getInstance();        
        if(mcClient == null) return "Minecraft client not available - no user defined";
        return mcClient.getUser().getName();
    }
        
}
