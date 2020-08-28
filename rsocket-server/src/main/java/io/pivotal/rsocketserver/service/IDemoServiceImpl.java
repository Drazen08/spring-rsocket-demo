package io.pivotal.rsocketserver.service;

import org.springframework.stereotype.Service;

/**
 * @author ：sunjx
 * @date ：Created in 2020/8/28 14:47
 * @description：
 */
@Service
public class IDemoServiceImpl implements IDemoService
{
    @Override
    public String sayHi() {
        return "hi";
    }
}
