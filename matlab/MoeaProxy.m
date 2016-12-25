classdef MoeaProxy < handle
    properties
        p % Process Java RMI Server
        s % Java IMoeaRmiServer Object
    end
    methods
        function obj = MoeaProxy()
            % 初始化 MoeaProxy 对象，同时启动 RMI 服务
            javaaddpath('./MOEA.jar');
            obj.p=java.lang.Runtime.getRuntime().exec('java -jar MOEA.jar');
            fname=char(moeatc.rmi.Constants.RMI_NOTIFY_FILE);
            while ~exist(fname,'file')
            end
            delete(fname);
            obj.s=moeatc.rmi.MoeaRmiFactory.getServer();
        end
        
        function tc = runMoea(this,algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision)
            % 执行MOEA算法
            % 输入：
            % this              MoeaProxy对象
            % algorithmName     MOEA算法名，支持:
            %   NSGA-II
            %   NSGA-III
            %   MOEAD
            %   eMOEA
            % problemName      问题名称
            % populationSize   种群大小
            % maxGenerations   最大迭代次数
            % nCalSize         计算均值的方差的代数
            % nCheckSize       比较均值和方差的代数
            % nPrecision       均值和方差近似的位数
            % 输出：
            % tc 停止条件对象，具有方法：
            %   getParetoFront          最终结果
            %   getDistances            距离变化
            %   getMeans                距离的均值
            %   getStds                 距离的标准差
            %   getNumberOfGenerations  终止迭代次数
            tc=this.s.runMOEA(algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision);
        end
        
        function delete(this)
            % 删除的同时，关闭 RMI 服务
            this.p.destroy();
        end
    end
end