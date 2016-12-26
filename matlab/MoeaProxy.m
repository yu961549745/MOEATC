classdef (Sealed) MoeaProxy < handle
    % MOEA RMI 代理对象 （单例）
    % clear all 才能删除该变量
    properties (Access = private)
        p % Process Java RMI Server
        s % Java IMoeaRmiServer Object
    end
    methods (Access = private)
        function obj = MoeaProxy()
            % 初始化 MoeaProxy 对象，同时启动 RMI 服务
            javaaddpath('./MOEA.jar');
            fname=char(moeatc.rmi.Constants.RMI_NOTIFY_FILE);
            if exist(fname,'file')
                delete(fname);
            end
            obj.p=java.lang.Runtime.getRuntime().exec('java -jar MOEA.jar noui');
            while ~exist(fname,'file')
            end
            delete(fname);
            obj.s=moeatc.rmi.MoeaRmiFactory.getServer();
        end
    end
    methods (Static)
        function singleObj = getInstance()
            % 获取实例
            persistent localObj
            if isempty(localObj) || ~isvalid(localObj)
                localObj = MoeaProxy();
            end
            singleObj = localObj;
        end
    end
    methods
        function [Variables,ParetoFront,Distances,Means,Stds,NumberOfGenerations,RecordPFs] = ...
                runMoea(this,algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision,recordGens)
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
            % recordGens       中间记录 Pareto Front
            % 输出：
            %   Variables            对应的变量
            %   ParetoFront          最终结果
            %   Distances            距离变化
            %   Means                距离的均值
            %   Stds                 距离的标准差
            %   NumberOfGenerations  终止迭代次数
            %   RecordPFs            记录的 Pareto Front
            if nargin<9
                recordGens=[];
            else
                recordGens=int32(recordGens);
            end
            tc=this.s.runMOEA(algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision,recordGens);
            Variables=tc.getVariables();
            ParetoFront=tc.getParetoFront();
            Distances=tc.getDistances();
            Means=tc.getMeans();
            Stds=tc.getStds();
            NumberOfGenerations=tc.getNumberOfGenerations();
            pfs=tc.getPfs();
            if isempty(pfs)
                RecordPFs={};
            else
                n=pfs.size();
                RecordPFs=cell(n,1);
                for i=1:n
                    RecordPFs{i}=pfs.get(i-1);
                end
            end
        end
        
        function delete(this)
            % 删除的同时，关闭 RMI 服务
            this.p.destroy();
        end
    end
end