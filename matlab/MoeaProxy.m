classdef (Sealed) MoeaProxy < handle
    % MOEA RMI ������� ��������
    % clear all ����ɾ���ñ���
    properties (Access = private)
        p % Process Java RMI Server
        s % Java IMoeaRmiServer Object
    end
    methods (Access = private)
        function obj = MoeaProxy()
            % ��ʼ�� MoeaProxy ����ͬʱ���� RMI ����
            javaaddpath('./MOEA.jar');
            fname=char(moeatc.rmi.Constants.RMI_NOTIFY_FILE);
            if exist(fname,'file')
                delete(fname);
            end
            obj.p=java.lang.Runtime.getRuntime().exec('java -jar MOEA.jar');
            while ~exist(fname,'file')
            end
            delete(fname);
            obj.s=moeatc.rmi.MoeaRmiFactory.getServer();
        end
    end
    methods (Static)
        function singleObj = getInstance()
            % ��ȡʵ��
            persistent localObj
            if isempty(localObj) || ~isvalid(localObj)
                localObj = MoeaProxy();
            end
            singleObj = localObj;
        end
    end
    methods
        function [Variables,ParetoFront,Distances,Means,Stds,NumberOfGenerations] = ...
                runMoea(this,algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision)
            % ִ��MOEA�㷨
            % ���룺
            % this              MoeaProxy����
            % algorithmName     MOEA�㷨����֧��:
            %   NSGA-II
            %   NSGA-III
            %   MOEAD
            %   eMOEA
            % problemName      ��������
            % populationSize   ��Ⱥ��С
            % maxGenerations   ����������
            % nCalSize         �����ֵ�ķ���Ĵ���
            % nCheckSize       �ȽϾ�ֵ�ͷ���Ĵ���
            % nPrecision       ��ֵ�ͷ�����Ƶ�λ��
            % �����
            %   Variables            ��Ӧ�ı���
            %   ParetoFront          ���ս��
            %   Distances            ����仯
            %   Means                ����ľ�ֵ
            %   Stds                 ����ı�׼��
            %   NumberOfGenerations  ��ֹ��������
            tc=this.s.runMOEA(algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision);
            Variables=tc.getVariables();
            ParetoFront=tc.getParetoFront();
            Distances=tc.getDistances();
            Means=tc.getMeans();
            Stds=tc.getStds();
            NumberOfGenerations=tc.getNumberOfGenerations();
        end
        
        function delete(this)
            % ɾ����ͬʱ���ر� RMI ����
            this.p.destroy();
        end
    end
end