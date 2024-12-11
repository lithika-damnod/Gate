import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-eye',
  standalone: false,
  template: `
    <svg width="25" height="14" viewBox="0 0 25 14" fill="none" xmlns="http://www.w3.org/2000/svg"
      class="pointer-events-none"
    >
      <g clip-path="url(#clip0_265_3144)">
        <rect x="9.39844" y="5.48047" width="5.94" height="5.94" rx="2.97" [attr.fill]="color" fill-opacity="0.6"/>
        <path d="M4.97136 6.93347C4.70574 6.84508 4.56057 6.55735 4.66537 6.29777C5.24265 4.86789 6.21728 3.62863 7.47871 2.72962C8.87806 1.7323 10.558 1.20524 12.2763 1.22444C13.9946 1.24364 15.6623 1.80813 17.039 2.83647C18.2801 3.76346 19.2267 5.02418 19.7719 6.46661C19.8709 6.72847 19.7193 7.01288 19.4518 7.09531L18.4043 7.41805C18.1368 7.50048 17.8551 7.34937 17.7504 7.08974C17.3406 6.07384 16.6592 5.18621 15.7764 4.52683C14.7579 3.76601 13.524 3.34838 12.2527 3.33417C10.9815 3.31996 9.73854 3.70991 8.70323 4.44777C7.80595 5.08726 7.10481 5.95944 6.6725 6.96593C6.56202 7.22315 6.27702 7.36792 6.0114 7.27954L4.97136 6.93347Z" [attr.fill]="color" fill-opacity="0.6"/>
      </g>
      <defs>
        <clipPath id="clip0_265_3144">
          <rect width="24" height="14" [attr.fill]="color" transform="translate(0.679688)"/>
        </clipPath>
      </defs>
    </svg>
  `,
})
export class EyeComponent {
  @Input() color = "white";
}
