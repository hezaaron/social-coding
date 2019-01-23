import { FormatTimePipe } from './format-time.pipe';

describe('FormatTimePipe', () => {
  const pipe = new FormatTimePipe();
  let timeInSeconds = 60;
  
  it('should create an instance', () => {
      expect(pipe).toBeTruthy();
  });
  
  it('should transform time', () => {
      expect(pipe.transform(timeInSeconds)).toEqual(`01:00`);
      timeInSeconds = 30;
      expect(pipe.transform(timeInSeconds)).toEqual(`00:30`);
      timeInSeconds = 0;
      expect(pipe.transform(timeInSeconds)).toEqual(`00:00`);
  });
  
});