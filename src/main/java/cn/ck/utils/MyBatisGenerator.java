package cn.ck.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MyBatisGenerator {

    private static String url = "jdbc:mysql://localhost:3306/testshiro?serverTimezone=UTC&useSSL=false&autoReconnect=true&tinyInt1isBit=false&useUnicode=true&characterEncoding=utf8";
    private static String user = "root";
    private static String password = "root";
    private static String dirverName = "com.mysql.jdbc.Driver";
    private static String outputDir = "src\\main\\java\\com\\changker\\demo";
    private static String packageName = "mybatisPlusGenerator";//生成的东西放在这个包里

    public static void main(String[] args){
        GlobalConfig config = new GlobalConfig();
        //数据源配置
        String dbUrl = url;
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername(user)
                .setPassword(password)
                .setDriverName(dirverName)
        ;
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
//                .entityTableFieldAnnotationEnable(enableTableFieldAnnotation)
//                .fieldPrefix(fieldPrefix)//test_id -> id, test_type -> type
//                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        ;
        config.setActiveRecord(false) // 不需要ActiveRecord特性的请改为false
                .setEnableCache(false) // XML 二级缓存
                .setOutputDir(outputDir)
                .setFileOverride(true)
                .setServiceName("%sService")
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(false)// XML columList
        ;
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

}
