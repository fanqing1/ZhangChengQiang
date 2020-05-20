package life.majiang.community.service;

import life.majiang.community.mapper.NavMapper;
import life.majiang.community.model.Nav;
import life.majiang.community.model.NavExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NavService {
    @Autowired(required = false)
    private NavMapper navMapper;

    public List<Nav> list() {
        NavExample navExample = new NavExample();
        navExample.createCriteria()
                .andStatusEqualTo(1);
        navExample.setOrderByClause("priority desc");
        List<Nav> navs = navMapper.selectByExample(navExample);
        return navs;
    }
}
