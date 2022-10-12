package mm.util.proxy;

import mm.client.proxy.ClientProxy;
import mm.server.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;

/**
 * Implemntation of the {@linkplain Proxy} interface.
 * Used by the mod to resolved sided resources.
 */
public class DefaultProxy implements Proxy{
    
    /**
     * Sided resolver instance.
     */
    Proxy sidedResolver; 

    /**
     * No-arg constructor.
     */
    DefaultProxy() {
        sidedResolver = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

    @Override
    public String getUser() {
        return sidedResolver.getUser();
    }
    
    /**
     * Factory method.
     * 
     * @return proxy instance.
     */
    public static Proxy getInstance() {
        return new DefaultProxy();
    }

}
