function y = cumvar(x)
y = cummean(x.^2)-cummean(x).^2;
end