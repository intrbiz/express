package com.intrbiz.express.script;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import com.intrbiz.express.SimpleEntityResolver;

public class TestScriptEngine
{
    private ExpressScriptEngineFactory factory = new ExpressScriptEngineFactory();
    
    @Test
    public void testDefineFunctions()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "function add(a, b) {\n" +
            "  return a + b;\n" +
            "}\n" +
            "return add(1, 2);"
        );
        for (int i = 0; i < 10; i++)
        {
            Object result = script.execute(script.createContext(), null);
            assertThat(result, is(instanceOf(Integer.class)));
            assertThat(result, is(equalTo(3)));
        }
    }
    
    @Test
    public void testIfStatement()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "if (x > 50) { return 'A'; }" +
            "else if (x > 25) { return 'B'; }" +
            "else { return 'C'; }"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("x", 100)), null);
        assertThat(result, is(equalTo("A")));
        // test else if path
        result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("x", 30)), null);
        assertThat(result, is(equalTo("B")));
        // test else path
        result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("x", 10)), null);
        assertThat(result, is(equalTo("C")));
    }
    
    @Test
    public void testShortIfStatement()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "if (x > 50)" +
            "  return 'A';" +
            "else if (x > 25)" + 
            "  return 'B';" +
            "else {" + 
            "  if (true)" +
            "    return 'C';" +
            "  else" +
            "    return 'D';" +
            "}"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("x", 100)), null);
        assertThat(result, is(equalTo("A")));
        // test else if path
        result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("x", 30)), null);
        assertThat(result, is(equalTo("B")));
        // test else path
        result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("x", 10)), null);
        assertThat(result, is(equalTo("C")));
    }
    
    
    @Test
    public void testForStatement()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "for (i = 0; i < 100; i++)" +
            "{" +
            "  sum += i;" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(equalTo(4950)));
    }
    
    @Test
    public void testForStatementWithContinueAndBreak()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "for (i = 0; i < 100; i++)" +
            "{" +
            "  if (i % 2 == 0)" +
            "  {" +
            "    continue;" +
            "  }" +
            "  sum += i;" +
            "  if (i >= 50)" +
            "  {" +
            "    break;" +
            "  }" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(equalTo(676)));
    }
    
    @Test
    public void testForEachStatement()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "for (i in numbers)" +
            "{" +
            "  sum += i;" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("numbers", IntStream.range(0, 100).mapToObj(Integer::new).collect(Collectors.toList()))), null);
        assertThat(result, is(equalTo(4950)));
    }
    
    @Test
    public void testForEachStatementWithContinueAndBreak()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "for (i in numbers)" +
            "{" +
            "  if (i % 2 == 0)" +
            "  {" +
            "    continue;" +
            "  }" +
            "  sum += i;" +
            "  if (i >= 50)" +
            "  {" +
            "    break;" +
            "  }" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("numbers", IntStream.range(0, 100).mapToObj(Integer::new).collect(Collectors.toList()))), null);
        assertThat(result, is(equalTo(676)));
    }
    
    @Test
    public void testWhileStatement()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "i = 0;" +
            "while (i < 100)" +
            "{" +
            "  sum += i;" +
            "  i++;" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(equalTo(4950)));
    }
    
    @Test
    public void testWhileStatementWithContinueAndBreak()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "i = 0;" +
            "while (i < 100)" +
            "{" +
            "  i++;" +
            "  if (i % 2 == 0)" +
            "  {" +
            "    continue;" +
            "  }" +
            "  sum += i;" +
            "  if (i >= 50)" +
            "  {" +
            "    break;" +
            "  }" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(equalTo(676)));
    }
    
    @Test
    public void testScriptExample()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "sum = 0;" +
            "i = 0;" +
            "while (i < 100)" +
            "{" +
            "  sum += i;" +
            "  i++;" +
            "}" +
            "return sum;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(equalTo(4950)));
    }
    
    @Test
    public void testScriptVerifyExample()
    {
        factory.verifyUnwrapped(
            "sum = 0;" +
            "i = 0;" +
            "while (i < 100)" +
            "{" +
            "  sum += i;" +
            "  i++;" +
            "}" +
            "return sum;"
        );
        assertTrue("Script verified", true);
    }
    
    
    @Test
    public void testScriptHaproxyExample()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "function parseStats(data)\n" + 
            "{\n" + 
            "    stats = {};\n" + 
            "    lines = data.split('\\n');\n" + 
            "    columns = lines[0].substring(1).split(',');\n" + 
            "    for (i = 1; i < lines.length; i++)\n" + 
            "    {\n" + 
            "        stat = {};\n" + 
            "        fields = lines[i].split(',');\n" + 
            "        for (j = 0; j < fields.length; j++)\n" + 
            "        {\n" + 
            "            stat.put(columns[j].trim(), fields[j]);\n" + 
            "        }\n" + 
            "        if (! stats[stat.pxname])\n" + 
            "        {\n" + 
            "            stats[stat.pxname] = { frontend: null, backend: null, servers: [] };\n" + 
            "        }\n" + 
            "        if (stat.svname == 'FRONTEND' || stat.svname == 'BACKEND')\n" + 
            "        {\n" + 
            "            stats[stat.pxname][stat.svname.toLowerCase()] = stat;\n" + 
            "        }\n" + 
            "        else\n" + 
            "        {\n" + 
            "            stats[stat.pxname].servers.add(stat);\n" + 
            "        }\n" + 
            "    }\n" + 
            "    return stats;\n" + 
            "}\n" + 
            "return parseStats(stats);"
        );
        // test input
        String stats = 
                "# pxname,svname,qcur,qmax,scur,smax,slim,stot,bin,bout,dreq,dresp,ereq,econ,eresp,wretr,wredis,status,weight,act,bck,chkfail,chkdown,lastchg,downtime,qlimit,pid,iid,sid,throttle,lbtot,tracked,type,rate,rate_lim,rate_max,check_status,check_code,check_duration,hrsp_1xx,hrsp_2xx,hrsp_3xx,hrsp_4xx,hrsp_5xx,hrsp_other,hanafail,req_rate,req_rate_max,req_tot,cli_abrt,srv_abrt,comp_in,comp_out,comp_byp,comp_rsp,lastsess,last_chk,last_agt,qtime,ctime,rtime,ttime,agent_status,agent_code,agent_duration,check_desc,agent_desc,check_rise,check_fall,check_health,agent_rise,agent_fall,agent_health,addr,cookie,mode,algo,conn_rate,conn_rate_max,conn_tot,intercepted,dcon,dses,wrew,connect,reuse,cache_lookups,cache_hits,\n" + 
                "stats,FRONTEND,,,1,1,10000,5,729,281946,0,0,0,,,,,OPEN,,,,,,,,,1,2,0,,,,0,1,0,1,,,,0,4,0,0,0,0,,1,1,5,,,0,0,0,0,,,,,,,,,,,,,,,,,,,,,http,,1,1,5,5,0,0,0,,,0,0,\n" + 
                "stats,sock-1,,,1,1,0,5,729,281946,0,0,0,,,,,OPEN,,,,,,,,,1,2,1,,,,3,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,http,,,,,,0,0,0,,,,,\n" + 
                "stats,BACKEND,0,0,0,0,1000,0,729,281946,0,0,,0,0,0,0,UP,0,0,0,,0,1722226,,,1,2,0,,0,,1,0,,0,,,,0,0,0,0,0,0,,,,0,0,0,0,0,0,0,0,,,0,0,0,1,,,,,,,,,,,,,,http,,,,,,,,0,0,0,0,0,\n" + 
                "https,FRONTEND,,,3,100,10000,128362,811014839,11656292962,419,0,4200,,,,,OPEN,,,,,,,,,1,3,0,,,,0,0,0,11,,,,2116,131224,23116,9868,8070,419,,0,164,174816,,,0,0,0,0,,,,,,,,,,,,,,,,,,,,,http,,0,43,128931,0,0,0,0,,,0,0,\n" + 
                "https,sock-1,,,3,100,0,128931,811014839,11656292962,419,0,4200,,,,,OPEN,,,,,,,,,1,3,1,,,,3,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,http,,,,,,0,0,0,,,,,\n" + 
                "http,FRONTEND,,,0,62,10000,13255,46000281,357457947,2454,0,928,,,,,OPEN,,,,,,,,,1,4,0,,,,0,0,0,64,,,,0,49049,4039,3246,3,2454,,0,64,58791,,,0,0,0,0,,,,,,,,,,,,,,,,,,,,,http,,0,64,13255,0,0,0,0,,,0,0,\n" + 
                "http,sock-1,,,0,62,0,13255,46000281,357457947,2454,0,928,,,,,OPEN,,,,,,,,,1,4,1,,,,3,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,http,,,,,,0,0,0,,,,,\n" + 
                "be_014193f6-0a03-4514-a8c1-77db68f4b546,srv-014193f6-0a03-49d8-ab41-1d0b2412a7b1-80,0,0,0,1,,158,69639,592520,,0,,0,0,0,0,UP,1,1,0,0,0,1722226,0,,1,7,1,,158,,2,0,,3,L7OK,200,185,0,156,0,2,0,0,,,,,0,0,,,,,16578,HTTP status check returned code <200>,,0,1,47,143,,,,Layer7 check passed,,3,2,4,,,,,,http,,,,,,,,0,158,0,,,\n" + 
                "be_014193f6-0a03-4514-a8c1-77db68f4b546,BACKEND,0,0,0,1,1,162,70364,593079,0,0,,0,0,0,0,UP,1,1,0,,0,1722226,0,,1,7,0,,158,,1,0,,3,,,,0,156,4,2,0,0,,,,162,0,0,0,0,0,0,16578,,,0,1,47,143,,,,,,,,,,,,,,http,,,,,,,,0,158,0,0,0,\n" + 
                "be_2ab19637-19fc-4026-a37b-10e59f7e8537,srv-2ab19637-19fc-45e7-bbac-f94814aa59d9-8080,0,0,0,2,,2352,604431,161633837,,0,,0,0,0,1,UP,1,1,0,28,14,134618,0,,1,18,1,,2352,,2,0,,11,L7OK,200,2,82,369,1900,0,0,0,,,,,1,0,,,,,101,HTTP status check returned code <200>,,0,0,3,4726,,,,Layer7 check passed,,3,2,4,,,,,,http,,,,,,,,0,1673,679,,,\n" + 
                "be_2ab19637-19fc-4026-a37b-10e59f7e8537,srv-2ab19637-19fc-466d-94ca-9a031b388fde-8080,0,0,0,2,,2352,555218,106866401,,0,,0,0,0,0,UP,1,1,0,28,14,132817,0,,1,18,2,,2352,,2,0,,10,L7OK,200,2,57,415,1880,0,0,0,,,,,0,0,,,,,1041,HTTP status check returned code <200>,,3,1,3,2919,,,,Layer7 check passed,,3,2,4,,,,,,http,,,,,,,,0,1667,685,,,\n" + 
                "be_2ab19637-19fc-4026-a37b-10e59f7e8537,srv-2ab19637-19fc-4f6d-a9f0-051771eb4f4e-8080,0,0,0,5,,2445,703837,135212731,,0,,0,0,0,1,UP,1,1,0,28,14,131019,0,,1,18,3,,2345,,2,0,,31,L7OK,200,2,86,505,1853,0,0,0,,,,,0,0,,,,,1002,HTTP status check returned code <200>,,0,0,5,3608,,,,Layer7 check passed,,3,2,4,,,,,,http,,,,,,,,0,1699,746,,,\n" + 
                "be_2ab19637-19fc-4026-a37b-10e59f7e8537,BACKEND,0,0,0,5,1,8151,2099264,403849614,0,0,,1,0,0,2,UP,3,3,0,,1,433821,42863,,1,18,0,,7049,,1,0,,32,,,,225,1289,6636,0,1,0,,,,8151,1,0,0,0,0,0,101,,,0,0,2,52,,,,,,,,,,,,,,http,,,,,,,,0,5039,2110,0,0,\n" + 
                "drop_http,BACKEND,0,0,0,2,2000,2873,595718,0,2873,0,,0,0,0,0,UP,0,0,0,,0,1722226,,,1,19,0,,0,,1,0,,10,,,,0,0,0,0,0,2873,,,,2873,0,0,0,0,0,0,-1,,,0,0,0,0,,,,,,,,,,,,,,http,,,,,,,,0,0,0,0,0,\n" + 
                "drop_tcp,BACKEND,0,0,0,0,1,0,0,0,0,0,,0,0,0,0,UP,0,0,0,,0,1722226,,,1,20,0,,0,,1,0,,0,,,,,,,,,,,,,,0,0,0,0,0,0,-1,,,0,0,0,0,,,,,,,,,,,,,,tcp,,,,,,,,0,0,0,,,\n" + 
                "acme_wellknown,10-10-0-5-80,0,0,0,0,,0,0,0,,0,,0,0,0,0,UP,1,1,0,0,0,1722226,0,,1,21,1,,0,,2,0,,0,L7OK,200,3,0,0,0,0,0,0,,,,,0,0,,,,,-1,HTTP status check returned code <200>,,0,0,0,0,,,,Layer7 check passed,,3,2,4,,,,,,http,,,,,,,,0,0,0,,,\n" + 
                "acme_wellknown,BACKEND,0,0,0,0,2000,0,0,0,0,0,,0,0,0,0,UP,1,1,0,,0,1722226,0,,1,21,0,,0,,1,0,,0,,,,0,0,0,0,0,0,,,,0,0,0,0,0,0,0,-1,,,0,0,0,0,,,,,,,,,,,,,,http,,,,,,,,0,0,0,0,0,\n" + 
                "";
        //
        Object result = script.execute(script.createContext(new SimpleEntityResolver().addEntity("stats", stats)), null);
        assertThat(result, is(instanceOf(Map.class)));
        assertThat(result.toString(), is(equalTo("{stats={frontend={pxname=stats, svname=FRONTEND, qcur=, qmax=, scur=1, smax=1, slim=10000, stot=5, bin=729, bout=281946, dreq=0, dresp=0, ereq=0, econ=, eresp=, wretr=, wredis=, status=OPEN, weight=, act=, bck=, chkfail=, chkdown=, lastchg=, downtime=, qlimit=, pid=1, iid=2, sid=0, throttle=, lbtot=, tracked=, type=0, rate=1, rate_lim=0, rate_max=1, check_status=, check_code=, check_duration=, hrsp_1xx=0, hrsp_2xx=4, hrsp_3xx=0, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=1, req_rate_max=1, req_tot=5, cli_abrt=, srv_abrt=, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=, last_chk=, last_agt=, qtime=, ctime=, rtime=, ttime=, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=1, conn_rate_max=1, conn_tot=5, intercepted=5, dcon=0, dses=0, wrew=0, connect=, reuse=, cache_lookups=0, cache_hits=0}, backend={pxname=stats, svname=BACKEND, qcur=0, qmax=0, scur=0, smax=0, slim=1000, stot=0, bin=729, bout=281946, dreq=0, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=0, act=0, bck=0, chkfail=, chkdown=0, lastchg=1722226, downtime=, qlimit=, pid=1, iid=2, sid=0, throttle=, lbtot=0, tracked=, type=1, rate=0, rate_lim=, rate_max=0, check_status=, check_code=, check_duration=, hrsp_1xx=0, hrsp_2xx=0, hrsp_3xx=0, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=0, cli_abrt=0, srv_abrt=0, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=0, last_chk=, last_agt=, qtime=0, ctime=0, rtime=0, ttime=1, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=0, reuse=0, cache_lookups=0, cache_hits=0}, servers=[{pxname=stats, svname=sock-1, qcur=, qmax=, scur=1, smax=1, slim=0, stot=5, bin=729, bout=281946, dreq=0, dresp=0, ereq=0, econ=, eresp=, wretr=, wredis=, status=OPEN, weight=, act=, bck=, chkfail=, chkdown=, lastchg=, downtime=, qlimit=, pid=1, iid=2, sid=1, throttle=, lbtot=, tracked=, type=3, rate=, rate_lim=, rate_max=, check_status=, check_code=, check_duration=, hrsp_1xx=, hrsp_2xx=, hrsp_3xx=, hrsp_4xx=, hrsp_5xx=, hrsp_other=, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=, srv_abrt=, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=, last_chk=, last_agt=, qtime=, ctime=, rtime=, ttime=, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=0, dses=0, wrew=0}]}, https={frontend={pxname=https, svname=FRONTEND, qcur=, qmax=, scur=3, smax=100, slim=10000, stot=128362, bin=811014839, bout=11656292962, dreq=419, dresp=0, ereq=4200, econ=, eresp=, wretr=, wredis=, status=OPEN, weight=, act=, bck=, chkfail=, chkdown=, lastchg=, downtime=, qlimit=, pid=1, iid=3, sid=0, throttle=, lbtot=, tracked=, type=0, rate=0, rate_lim=0, rate_max=11, check_status=, check_code=, check_duration=, hrsp_1xx=2116, hrsp_2xx=131224, hrsp_3xx=23116, hrsp_4xx=9868, hrsp_5xx=8070, hrsp_other=419, hanafail=, req_rate=0, req_rate_max=164, req_tot=174816, cli_abrt=, srv_abrt=, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=, last_chk=, last_agt=, qtime=, ctime=, rtime=, ttime=, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=0, conn_rate_max=43, conn_tot=128931, intercepted=0, dcon=0, dses=0, wrew=0, connect=, reuse=, cache_lookups=0, cache_hits=0}, backend=null, servers=[{pxname=https, svname=sock-1, qcur=, qmax=, scur=3, smax=100, slim=0, stot=128931, bin=811014839, bout=11656292962, dreq=419, dresp=0, ereq=4200, econ=, eresp=, wretr=, wredis=, status=OPEN, weight=, act=, bck=, chkfail=, chkdown=, lastchg=, downtime=, qlimit=, pid=1, iid=3, sid=1, throttle=, lbtot=, tracked=, type=3, rate=, rate_lim=, rate_max=, check_status=, check_code=, check_duration=, hrsp_1xx=, hrsp_2xx=, hrsp_3xx=, hrsp_4xx=, hrsp_5xx=, hrsp_other=, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=, srv_abrt=, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=, last_chk=, last_agt=, qtime=, ctime=, rtime=, ttime=, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=0, dses=0, wrew=0}]}, http={frontend={pxname=http, svname=FRONTEND, qcur=, qmax=, scur=0, smax=62, slim=10000, stot=13255, bin=46000281, bout=357457947, dreq=2454, dresp=0, ereq=928, econ=, eresp=, wretr=, wredis=, status=OPEN, weight=, act=, bck=, chkfail=, chkdown=, lastchg=, downtime=, qlimit=, pid=1, iid=4, sid=0, throttle=, lbtot=, tracked=, type=0, rate=0, rate_lim=0, rate_max=64, check_status=, check_code=, check_duration=, hrsp_1xx=0, hrsp_2xx=49049, hrsp_3xx=4039, hrsp_4xx=3246, hrsp_5xx=3, hrsp_other=2454, hanafail=, req_rate=0, req_rate_max=64, req_tot=58791, cli_abrt=, srv_abrt=, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=, last_chk=, last_agt=, qtime=, ctime=, rtime=, ttime=, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=0, conn_rate_max=64, conn_tot=13255, intercepted=0, dcon=0, dses=0, wrew=0, connect=, reuse=, cache_lookups=0, cache_hits=0}, backend=null, servers=[{pxname=http, svname=sock-1, qcur=, qmax=, scur=0, smax=62, slim=0, stot=13255, bin=46000281, bout=357457947, dreq=2454, dresp=0, ereq=928, econ=, eresp=, wretr=, wredis=, status=OPEN, weight=, act=, bck=, chkfail=, chkdown=, lastchg=, downtime=, qlimit=, pid=1, iid=4, sid=1, throttle=, lbtot=, tracked=, type=3, rate=, rate_lim=, rate_max=, check_status=, check_code=, check_duration=, hrsp_1xx=, hrsp_2xx=, hrsp_3xx=, hrsp_4xx=, hrsp_5xx=, hrsp_other=, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=, srv_abrt=, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=, last_chk=, last_agt=, qtime=, ctime=, rtime=, ttime=, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=0, dses=0, wrew=0}]}, be_014193f6-0a03-4514-a8c1-77db68f4b546={frontend=null, backend={pxname=be_014193f6-0a03-4514-a8c1-77db68f4b546, svname=BACKEND, qcur=0, qmax=0, scur=0, smax=1, slim=1, stot=162, bin=70364, bout=593079, dreq=0, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=1, act=1, bck=0, chkfail=, chkdown=0, lastchg=1722226, downtime=0, qlimit=, pid=1, iid=7, sid=0, throttle=, lbtot=158, tracked=, type=1, rate=0, rate_lim=, rate_max=3, check_status=, check_code=, check_duration=, hrsp_1xx=0, hrsp_2xx=156, hrsp_3xx=4, hrsp_4xx=2, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=162, cli_abrt=0, srv_abrt=0, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=16578, last_chk=, last_agt=, qtime=0, ctime=1, rtime=47, ttime=143, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=158, reuse=0, cache_lookups=0, cache_hits=0}, servers=[{pxname=be_014193f6-0a03-4514-a8c1-77db68f4b546, svname=srv-014193f6-0a03-49d8-ab41-1d0b2412a7b1-80, qcur=0, qmax=0, scur=0, smax=1, slim=, stot=158, bin=69639, bout=592520, dreq=, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=1, act=1, bck=0, chkfail=0, chkdown=0, lastchg=1722226, downtime=0, qlimit=, pid=1, iid=7, sid=1, throttle=, lbtot=158, tracked=, type=2, rate=0, rate_lim=, rate_max=3, check_status=L7OK, check_code=200, check_duration=185, hrsp_1xx=0, hrsp_2xx=156, hrsp_3xx=0, hrsp_4xx=2, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=0, srv_abrt=0, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=16578, last_chk=HTTP status check returned code <200>, last_agt=, qtime=0, ctime=1, rtime=47, ttime=143, agent_status=, agent_code=, agent_duration=, check_desc=Layer7 check passed, agent_desc=, check_rise=3, check_fall=2, check_health=4, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=158, reuse=0}]}, be_2ab19637-19fc-4026-a37b-10e59f7e8537={frontend=null, backend={pxname=be_2ab19637-19fc-4026-a37b-10e59f7e8537, svname=BACKEND, qcur=0, qmax=0, scur=0, smax=5, slim=1, stot=8151, bin=2099264, bout=403849614, dreq=0, dresp=0, ereq=, econ=1, eresp=0, wretr=0, wredis=2, status=UP, weight=3, act=3, bck=0, chkfail=, chkdown=1, lastchg=433821, downtime=42863, qlimit=, pid=1, iid=18, sid=0, throttle=, lbtot=7049, tracked=, type=1, rate=0, rate_lim=, rate_max=32, check_status=, check_code=, check_duration=, hrsp_1xx=225, hrsp_2xx=1289, hrsp_3xx=6636, hrsp_4xx=0, hrsp_5xx=1, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=8151, cli_abrt=1, srv_abrt=0, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=101, last_chk=, last_agt=, qtime=0, ctime=0, rtime=2, ttime=52, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=5039, reuse=2110, cache_lookups=0, cache_hits=0}, servers=[{pxname=be_2ab19637-19fc-4026-a37b-10e59f7e8537, svname=srv-2ab19637-19fc-45e7-bbac-f94814aa59d9-8080, qcur=0, qmax=0, scur=0, smax=2, slim=, stot=2352, bin=604431, bout=161633837, dreq=, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=1, status=UP, weight=1, act=1, bck=0, chkfail=28, chkdown=14, lastchg=134618, downtime=0, qlimit=, pid=1, iid=18, sid=1, throttle=, lbtot=2352, tracked=, type=2, rate=0, rate_lim=, rate_max=11, check_status=L7OK, check_code=200, check_duration=2, hrsp_1xx=82, hrsp_2xx=369, hrsp_3xx=1900, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=1, srv_abrt=0, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=101, last_chk=HTTP status check returned code <200>, last_agt=, qtime=0, ctime=0, rtime=3, ttime=4726, agent_status=, agent_code=, agent_duration=, check_desc=Layer7 check passed, agent_desc=, check_rise=3, check_fall=2, check_health=4, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=1673, reuse=679}, {pxname=be_2ab19637-19fc-4026-a37b-10e59f7e8537, svname=srv-2ab19637-19fc-466d-94ca-9a031b388fde-8080, qcur=0, qmax=0, scur=0, smax=2, slim=, stot=2352, bin=555218, bout=106866401, dreq=, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=1, act=1, bck=0, chkfail=28, chkdown=14, lastchg=132817, downtime=0, qlimit=, pid=1, iid=18, sid=2, throttle=, lbtot=2352, tracked=, type=2, rate=0, rate_lim=, rate_max=10, check_status=L7OK, check_code=200, check_duration=2, hrsp_1xx=57, hrsp_2xx=415, hrsp_3xx=1880, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=0, srv_abrt=0, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=1041, last_chk=HTTP status check returned code <200>, last_agt=, qtime=3, ctime=1, rtime=3, ttime=2919, agent_status=, agent_code=, agent_duration=, check_desc=Layer7 check passed, agent_desc=, check_rise=3, check_fall=2, check_health=4, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=1667, reuse=685}, {pxname=be_2ab19637-19fc-4026-a37b-10e59f7e8537, svname=srv-2ab19637-19fc-4f6d-a9f0-051771eb4f4e-8080, qcur=0, qmax=0, scur=0, smax=5, slim=, stot=2445, bin=703837, bout=135212731, dreq=, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=1, status=UP, weight=1, act=1, bck=0, chkfail=28, chkdown=14, lastchg=131019, downtime=0, qlimit=, pid=1, iid=18, sid=3, throttle=, lbtot=2345, tracked=, type=2, rate=0, rate_lim=, rate_max=31, check_status=L7OK, check_code=200, check_duration=2, hrsp_1xx=86, hrsp_2xx=505, hrsp_3xx=1853, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=0, srv_abrt=0, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=1002, last_chk=HTTP status check returned code <200>, last_agt=, qtime=0, ctime=0, rtime=5, ttime=3608, agent_status=, agent_code=, agent_duration=, check_desc=Layer7 check passed, agent_desc=, check_rise=3, check_fall=2, check_health=4, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=1699, reuse=746}]}, drop_http={frontend=null, backend={pxname=drop_http, svname=BACKEND, qcur=0, qmax=0, scur=0, smax=2, slim=2000, stot=2873, bin=595718, bout=0, dreq=2873, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=0, act=0, bck=0, chkfail=, chkdown=0, lastchg=1722226, downtime=, qlimit=, pid=1, iid=19, sid=0, throttle=, lbtot=0, tracked=, type=1, rate=0, rate_lim=, rate_max=10, check_status=, check_code=, check_duration=, hrsp_1xx=0, hrsp_2xx=0, hrsp_3xx=0, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=2873, hanafail=, req_rate=, req_rate_max=, req_tot=2873, cli_abrt=0, srv_abrt=0, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=-1, last_chk=, last_agt=, qtime=0, ctime=0, rtime=0, ttime=0, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=0, reuse=0, cache_lookups=0, cache_hits=0}, servers=[]}, drop_tcp={frontend=null, backend={pxname=drop_tcp, svname=BACKEND, qcur=0, qmax=0, scur=0, smax=0, slim=1, stot=0, bin=0, bout=0, dreq=0, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=0, act=0, bck=0, chkfail=, chkdown=0, lastchg=1722226, downtime=, qlimit=, pid=1, iid=20, sid=0, throttle=, lbtot=0, tracked=, type=1, rate=0, rate_lim=, rate_max=0, check_status=, check_code=, check_duration=, hrsp_1xx=, hrsp_2xx=, hrsp_3xx=, hrsp_4xx=, hrsp_5xx=, hrsp_other=, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=0, srv_abrt=0, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=-1, last_chk=, last_agt=, qtime=0, ctime=0, rtime=0, ttime=0, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=tcp, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=0, reuse=0}, servers=[]}, acme_wellknown={frontend=null, backend={pxname=acme_wellknown, svname=BACKEND, qcur=0, qmax=0, scur=0, smax=0, slim=2000, stot=0, bin=0, bout=0, dreq=0, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=1, act=1, bck=0, chkfail=, chkdown=0, lastchg=1722226, downtime=0, qlimit=, pid=1, iid=21, sid=0, throttle=, lbtot=0, tracked=, type=1, rate=0, rate_lim=, rate_max=0, check_status=, check_code=, check_duration=, hrsp_1xx=0, hrsp_2xx=0, hrsp_3xx=0, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=0, cli_abrt=0, srv_abrt=0, comp_in=0, comp_out=0, comp_byp=0, comp_rsp=0, lastsess=-1, last_chk=, last_agt=, qtime=0, ctime=0, rtime=0, ttime=0, agent_status=, agent_code=, agent_duration=, check_desc=, agent_desc=, check_rise=, check_fall=, check_health=, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=0, reuse=0, cache_lookups=0, cache_hits=0}, servers=[{pxname=acme_wellknown, svname=10-10-0-5-80, qcur=0, qmax=0, scur=0, smax=0, slim=, stot=0, bin=0, bout=0, dreq=, dresp=0, ereq=, econ=0, eresp=0, wretr=0, wredis=0, status=UP, weight=1, act=1, bck=0, chkfail=0, chkdown=0, lastchg=1722226, downtime=0, qlimit=, pid=1, iid=21, sid=1, throttle=, lbtot=0, tracked=, type=2, rate=0, rate_lim=, rate_max=0, check_status=L7OK, check_code=200, check_duration=3, hrsp_1xx=0, hrsp_2xx=0, hrsp_3xx=0, hrsp_4xx=0, hrsp_5xx=0, hrsp_other=0, hanafail=, req_rate=, req_rate_max=, req_tot=, cli_abrt=0, srv_abrt=0, comp_in=, comp_out=, comp_byp=, comp_rsp=, lastsess=-1, last_chk=HTTP status check returned code <200>, last_agt=, qtime=0, ctime=0, rtime=0, ttime=0, agent_status=, agent_code=, agent_duration=, check_desc=Layer7 check passed, agent_desc=, check_rise=3, check_fall=2, check_health=4, agent_rise=, agent_fall=, agent_health=, addr=, cookie=, mode=http, algo=, conn_rate=, conn_rate_max=, conn_tot=, intercepted=, dcon=, dses=, wrew=0, connect=0, reuse=0}]}}")));
    }
    
    @Test
    public void testJsonLike()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "x = {\n" + 
            "    id: 'test123',\n" + 
            "    array: [\n" + 
            "        1,\n" + 
            "        2,\n" + 
            "        3\n" + 
            "    ],\n" + 
            "    object: {\n" + 
            "        a: 'A',\n" + 
            "        b: 'B'\n" + 
            "    },\n" + 
            "    exp: (1 + 2 * 3 / 4)^2\n" + 
            "};\n" + 
            "return x;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(instanceOf(Map.class)));
        assertThat(result.toString(), is(equalTo("{id=test123, array=[1, 2, 3], object={a=A, b=B}, exp=4.0}")));
    }
    
    @Test
    public void testMapAssignment()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "m = {};\n" + 
            "m['test'] = 123;\n" + 
            "m[1 + 2] = 345;\n" + 
            "return m;"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(instanceOf(Map.class)));
        assertThat(result.toString(), is(equalTo("{test=123, 3=345}")));
    }
    
    @Test
    public void testScriptExampleWithComments()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "/* test comment */\n" +
            "sum = 0;\n" +
            "i = 0;\n" +
            "while (i < 100)\n" +
            "{\n" +
            "  /** test */\n" +
            "  sum += i;\n" +
            "  i++;\n" +
            "}\n" +
            "// testing\n" +
            "return sum;\n"
        );
        // test if path
        Object result = script.execute(script.createContext(new SimpleEntityResolver()), null);
        assertThat(result, is(equalTo(4950)));
    }
    
    @Test
    public void testScriptExample3()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "            function parseStats(data)\n" + 
            "            {\n" + 
            "                stats = {};\n" + 
            "                lines = data.split('\\n');\n" + 
            "                columns = lines[0].substring(1).split(',');\n" + 
            "                for (i = 1; i < int(lines); i++)\n" + 
            "                {\n" + 
            "                    stat = {};\n" + 
            "                    fields = lines[i].split(',');\n" + 
            "                    for (j = 0; j < fields.length; j++)\n" + 
            "                    {\n" + 
            "                        stat.put(columns[j].trim(), fields[j]);\n" + 
            "                    }\n" + 
            "                    if (! stats[stat.pxname])\n" + 
            "                    {\n" + 
            "                        stats[stat.pxname] = { frontend: null, backend: null, servers: [] };\n" + 
            "                    }\n" + 
            "                    if (stat.svname == 'FRONTEND' || stat.svname == 'BACKEND')\n" + 
            "                    {\n" + 
            "                        stats[stat.pxname][stat.svname.toLowerCase()] = stat;\n" + 
            "                    }\n" + 
            "                    else\n" + 
            "                    {\n" + 
            "                        stats[stat.pxname].servers.add(stat);\n" + 
            "                    }\n" + 
            "                }\n" + 
            "                return stats;\n" + 
            "            }\n" + 
            "            return parseStats(stats);\n" + 
            "        \n" + 
            "        \n" + 
            "\n" + 
            "\n" + 
            "        \n" + 
            "            /* Validate parameters */\n" + 
            "            bergamot.require('host');\n" + 
            "            bergamot.require('port');\n" + 
            "            bergamot.require('username');\n" + 
            "            bergamot.require('password');\n" + 
            "            bergamot.require('proxy_name');\n" + 
            "            /* Execute */\n" + 
            "            http.check()\n" + 
            "            .connect(check.getParameter('host'))\n" + 
            "            .port(check.getIntParameter('port'))\n" + 
            "            .get(check.getParameter('path'))\n" + 
            "            .basicAuth(check.getParameter('username'), check.getParameter('password'))\n" + 
            "            .execute((r) -> {\n" + 
            "                    /* API Error? */\n" + 
            "                    if (r.status() != 200)\n" + 
            "                    { \n" + 
            "                        bergamot.error('HAProxy API returned: ' + r.status());\n" + 
            "                        return;\n" + 
            "                    }\n" + 
            "                    /* Parse the CSV into something useful */\n" + 
            "                    stats = parseStats(r.content());\n" + 
            "                    /* Get the stats */\n" + 
            "                    proxy = stats[check.getParameter('proxy_name')];\n" + 
            "                    if (! proxy)\n" + 
            "                    {\n" + 
            "                        bergamot.error('HAProxy has no such proxy: ' + check.getParameter('proxy_name'));\n" + 
            "                        return;\n" + 
            "                    }\n" + 
            "                    /* Count the up servers */\n" + 
            "                    upCount = 0;\n" + 
            "                    outputs = [];\n" + 
            "                    for (server in proxy.servers)\n" + 
            "                    {\n" + 
            "                        if (server.status === 'UP')\n" + 
            "                        {\n" + 
            "                            upCount++;\n" + 
            "                        }\n" + 
            "                        outputs.add(\n" + 
            "                          server.svname + ' ' + server.status + ' (' + server.check_status + ') for ' + \n" + 
            "                          long(long(server.lastchg) / 3600) + 'h' + long((long(server.lastchg) % 3600) / 60) + 'm'\n" + 
            "                        );\n" + 
            "                    }    \n" + 
            "                    /* */\n" + 
            "                    bergamot.applyLessThanThreshold(\n" + 
            "                        upCount,\n" + 
            "                        upCount + ' UP - ' + join(', ', outputs)\n" + 
            "                    );\n" + 
            "                    bergamot.readings()\n" + 
            "                        .longGaugeReading('current_sessions',  null, long(proxy.backend.scur))\n" + 
            "                        .longGaugeReading('max_sessions', null, long(proxy.backend.smax))\n" + 
            "                        .longGaugeReading('total_sessions', null, long(proxy.backend.stot))\n" + 
            "                        .longGaugeReading('sessions_limit', null, long(proxy.backend.slim))\n" + 
            "                        .publish();\n" + 
            "                }, \n" + 
            "                (e) -> { \n" + 
            "                    bergamot.error(e); \n" + 
            "            });"
        );
        // test if path
        System.out.println(script);
    }
    
    @Test
    public void testScriptExample4()
    {
        ExpressScriptEngine script = factory.parseUnwrapped(
            "        \n" + 
            "            /* Validate parameters */\n" + 
            "            bergamot.require('host');\n" + 
            "            bergamot.require('port');\n" + 
            "            bergamot.require('username');\n" + 
            "            bergamot.require('password');\n" + 
            "            bergamot.require('proxy_name');\n" + 
            "            /* Execute */\n" + 
            "            http.check()\n" + 
            "            .execute((e) -> { \n" + 
            "                    bergamot.error(e); \n" + 
            "            });"
        );
        // test if path
        System.out.println(script);
    }
    
    @Test
    public void testScriptExample5()
    {
        factory.verifyUnwrapped(
            "                    /* API Error? */\n" + 
            "                    if (r.status() != 200)\n" + 
            "                    { \n" + 
            "                        bergamot.error('HAProxy API returned: ' + r.status());\n" + 
            "                        return;\n" + 
            "                    }\n" + 
            "                    /* Parse the CSV into something useful */\n" + 
            "                    stats = parseStats(r.content());\n" + 
            "                    /* Get the stats */\n" + 
            "                    proxy = stats[check.getParameter('proxy_name')];\n" + 
            "                    if (! proxy)\n" + 
            "                    {\n" + 
            "                        bergamot.error('HAProxy has no such proxy: ' + check.getParameter('proxy_name'));\n" + 
            "                        return;\n" + 
            "                    }\n" + 
            "                    /* Count the up servers */\n" + 
            "                    upCount = 0;\n" + 
            "                    outputs = [];\n" + 
            "                    for (server : proxy.servers)\n" + 
            "                    {\n" + 
            "                        if (server.status === 'UP')\n" + 
            "                        {\n" + 
            "                            upCount++;\n" + 
            "                        }\n" + 
            "                        outputs.add(\n" + 
            "                          server.svname + ' ' + server.status + ' (' + server.check_status + ') for ' + \n" + 
            "                          long(long(server.lastchg) / 3600) + 'h' + long((long(server.lastchg) % 3600) / 60) + 'm'\n" + 
            "                        );\n" + 
            "                    }    \n" + 
            "                    /* */\n" + 
            "                    bergamot.applyLessThanThreshold(\n" + 
            "                        upCount,\n" + 
            "                        upCount + ' UP - ' + join(', ', outputs)\n" + 
            "                    );\n" + 
            "                    bergamot.readings()\n" + 
            "                        .longGaugeReading('current_sessions',  null, long(proxy.backend.scur))\n" + 
            "                        .longGaugeReading('max_sessions', null, long(proxy.backend.smax))\n" + 
            "                        .longGaugeReading('total_sessions', null, long(proxy.backend.stot))\n" + 
            "                        .longGaugeReading('sessions_limit', null, long(proxy.backend.slim))\n" + 
            "                        .publish();\n"
        );
    }
}
