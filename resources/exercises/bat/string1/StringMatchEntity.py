# BEGIN TEMPLATE
def stringMatch(a, b):
  # BEGIN SOLUTION
  l = min( len(a), len(b) )
  count = 0
  for i in range(l-1):
    if a[i:i+2] == b[i:i+2]:
      count += 1
  return count

# END SOLUTION
# END TEMPLATE
