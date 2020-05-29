package com.leyou.client;

import com.leyou.client.SpuClientServer;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface SpuClient extends SpuClientServer {


}
