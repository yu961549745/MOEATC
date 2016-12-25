function y = cummean(x)
x=x(:);
n=length(x);
y=cumsum(x)./(1:n)';
end