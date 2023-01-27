//
//  GradientSurface.swift
//  iosApp
//
//  Created by user on 24.01.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GradientSurface: ViewModifier {
    @Environment(\.colorScheme) var colorSheme
    
    func body(content: Content) -> some View {
        if colorSheme == .dark {
            let startColor = Color(hex: 0xFF23262E)
            let endColor = Color(hex: 0xFF212329)
            content.background(
                LinearGradient(
                    gradient: Gradient(
                        colors: [startColor, endColor]),
                    startPoint: .top,
                    endPoint: .bottom
                )
            )
        } else {
            content.background(Color.surface)
        }
    }
}

extension View {
    func gradientSurface() -> some View {
        modifier(GradientSurface())
    }
}
