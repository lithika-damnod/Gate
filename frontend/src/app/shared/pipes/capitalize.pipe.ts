import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'capitalize',
  standalone: false
})
export class CapitalizePipe implements PipeTransform {

  transform(value: string): string {
    if (!value) return value;  // Return the value as it is if it's empty or null
    value = value.toLowerCase();
    return value.charAt(0).toUpperCase() + value.slice(1);  // Capitalize the first letter only
  }
}
