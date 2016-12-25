classdef MoeaProxy < handle
    properties
        p % Process Java RMI Server
        s % Java IMoeaRmiServer Object
    end
    methods
        function obj = MoeaProxy()
            % ��ʼ�� MoeaProxy ����ͬʱ���� RMI ����
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
            % tc ֹͣ�������󣬾��з�����
            %   getParetoFront          ���ս��
            %   getDistances            ����仯
            %   getMeans                ����ľ�ֵ
            %   getStds                 ����ı�׼��
            %   getNumberOfGenerations  ��ֹ��������
            tc=this.s.runMOEA(algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision);
        end
        
        function delete(this)
            % ɾ����ͬʱ���ر� RMI ����
            this.p.destroy();
        end
    end
end