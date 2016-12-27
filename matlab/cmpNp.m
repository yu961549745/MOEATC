% 比较不同的np的影响
clc,clear,close all;
disp('加载MOEA服务')
tic
proxy=MoeaProxy.getInstance();
toc
algorithmName='NSGA-III';
problemNames={'Binh2','Viennet2','CF2','CF10','LZ1','UF8'};
populationSize=100;
maxGenerations=2000;
nCalSize=100;
nCheckSize=10;
nPrecisions=[1,2,3];
TestTimes=10;

m=length(problemNames);
n=length(nPrecisions);
MD=zeros(m,n);SD=zeros(m,n);
MN=zeros(m,n);SN=zeros(m,n);
for i=1:m
    for j=1:n
        problemName=problemNames{i};
        nPrecision=nPrecisions(j);
        dpq=zeros(TestTimes,1);
        ngt=zeros(TestTimes,1);
        for k=1:TestTimes
            [~,~,~,m]=runMoea(proxy,algorithmName,problemName,populationSize,...
                maxGenerations,nCalSize,nCheckSize,nPrecision);
            dpq(k)=m(end);
            ngt(k)=length(m);
        end
        MD(i,j)=mean(dpq);SD(i,j)=std(dpq,1);
        MN(i,j)=mean(ngt);SN(i,j)=std(ngt,1);
        fprintf('%s %d %f±%f %f±%f\n',problemName,nPrecision,...
            MD(i,j),SD(i,j),MN(i,j),SN(i,j));
    end
end

for i=1:m
    fprintf(fid,'%s,',problemNames{i});
    for j=1:n
        fprintf(fid,'%.0f±%.0f,',MN(i,j),SN(i,j));
    end
    for j=1:n
        fprintf(fid,'%.4f±%.4f,',MD(i,j),SD(i,j));
    end
    fprintf(fid,'\n');
end
fclose(fid);